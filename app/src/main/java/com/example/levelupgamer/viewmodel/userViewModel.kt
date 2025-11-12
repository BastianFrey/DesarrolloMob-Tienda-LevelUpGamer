package com.example.levelupgamer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.database.AppDatabase
import com.example.levelupgamer.data.model.User
import com.example.levelupgamer.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    init {
        val dao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(dao)
    }

    private fun calcularNivel(puntos: Int): Int {
        return when {
            puntos >= 5000 -> 5 // LEYENDA
            puntos >= 2000 -> 4 // MAESTRO
            puntos >= 500 -> 3  // VETERANO
            puntos >= 100 -> 2  // INICIADO
            else -> 1           // NOVATO
        }
    }

    fun register(
        nombre: String,
        correo: String,
        contrasena: String,
        anioNacimiento: Int,
        codigoReferido: String? = null,
        onComplete: (Boolean, String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val newUser = User(
                    nombre = nombre,
                    correo = correo,
                    contrasena = contrasena,
                    anioNacimiento = anioNacimiento,
                    puntosLevelUp = 0,
                    nivelGamer = 1
                )
                repository.register(newUser)

                if (!codigoReferido.isNullOrBlank()) {

                    val referidor = repository.getUserByNombre(codigoReferido)

                    if (referidor != null) {
                        val puntosPorReferido = 100 // Puntos a otorgar
                        val nuevosPuntosReferidor = referidor.puntosLevelUp + puntosPorReferido
                        val nuevoNivelReferidor = calcularNivel(nuevosPuntosReferidor)

                        val updatedReferidor = referidor.copy(
                            puntosLevelUp = nuevosPuntosReferidor,
                            nivelGamer = nuevoNivelReferidor
                        )
                        repository.updateUser(updatedReferidor)
                    }
                }

                onComplete(true, null)

            } catch (e: Exception) {
                onComplete(false, e.message ?: "Error desconocido al registrar usuario.")
            }
        }
    }

    fun agregarPuntos(puntosGanados: Int, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = _currentUser.value ?: return@launch

            val nuevosPuntos = user.puntosLevelUp + puntosGanados
            val nuevoNivel = calcularNivel(nuevosPuntos)

            val updatedUser = user.copy(
                puntosLevelUp = nuevosPuntos,
                nivelGamer = nuevoNivel
            )

            try {
                repository.updateUser(updatedUser)
                _currentUser.value = updatedUser
                onComplete(true)
            } catch (e: Exception) {
                onComplete(false)
            }
        }
    }

    fun login(nombre: String, contrasena: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = repository.login(nombre, contrasena)
            _currentUser.value = user
            onComplete(user != null)
        }
    }

    fun updateUser(user: User, onComplete: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                repository.updateUser(user)
                _currentUser.value = user
                onComplete(true, null)
            } catch (e: Exception) {
                onComplete(false, e.message)
            }
        }
    }

    fun logout() {
        _currentUser.value = null
    }

    fun loadCurrentUser(nombre: String, contrasena: String) {
        viewModelScope.launch {
            val user = repository.login(nombre, contrasena)
            _currentUser.value = user
        }
    }
}