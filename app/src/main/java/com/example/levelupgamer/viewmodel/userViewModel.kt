package com.example.levelupgamer.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.database.AppDatabase
import com.example.levelupgamer.data.model.LoginRequest
import com.example.levelupgamer.data.model.User
import com.example.levelupgamer.data.network.RetrofitClient
import com.example.levelupgamer.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    var authToken: String? = null
        private set

    init {
        val dao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(dao)
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

                val response = RetrofitClient.instance.registrarUsuario(newUser)

                if (response.isSuccessful) {
                    Log.d("API_REGISTER", "Usuario registrado: ${response.body()}")
                    onComplete(true, null)
                } else {
                    Log.e("API_REGISTER", "Error: ${response.code()} - ${response.errorBody()?.string()}")
                    onComplete(false, "Error al registrar. Verifique sus datos.")
                }

            } catch (e: Exception) {
                Log.e("API_REGISTER", "Fallo conexión: ${e.message}")
                onComplete(false, "Error de conexión con el servidor.")
            }
        }
    }

    fun login(nombre: String, contrasena: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(correo = nombre, contrasena = contrasena)

                val response = RetrofitClient.instance.login(request)

                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!

                    authToken = "Bearer ${loginResponse.token}"

                    val userLogged = User(
                        id = loginResponse.usuarioId.toInt(),
                        nombre = nombre,
                        correo = nombre,
                        contrasena = "",
                        anioNacimiento = 0,
                        puntosLevelUp = 0,
                        nivelGamer = 1
                    )
                    _currentUser.value = userLogged
                    onComplete(true)
                } else {
                    onComplete(false)
                }
            } catch (e: Exception) {
                Log.e("API_LOGIN", "Error: ${e.message}")
                onComplete(false)
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

    private fun calcularNivel(puntos: Int): Int {
        return when {
            puntos >= 5000 -> 5 // LEYENDA
            puntos >= 2000 -> 4 // MAESTRO
            puntos >= 500 -> 3  // VETERANO
            puntos >= 100 -> 2  // INICIADO
            else -> 1           // NOVATO
        }
    }

    fun logout() {
        _currentUser.value = null
        authToken = null
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
}