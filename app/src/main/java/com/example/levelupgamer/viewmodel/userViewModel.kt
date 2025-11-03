package com.example.levelupgamer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.AppDatabase
import com.example.levelupgamer.data.User
import com.example.levelupgamer.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val dao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(dao)
    }

    fun register(nombre: String, correo: String, contrasena: String, anioNacimiento: Int, onComplete: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                repository.register(User(nombre = nombre, correo = correo, contrasena = contrasena, anioNacimiento = anioNacimiento))
                onComplete(true, null)
            } catch (e: Exception) {
                onComplete(false, e.message)
            }
        }
    }

    fun login(nombre: String, contrasena: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = repository.login(nombre, contrasena)
            onComplete(user != null)
        }
    }
}
