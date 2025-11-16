package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.repository.AuthRepository
import com.example.levelupgamer.ui.screens.login.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
// --- ¡Importaciones nuevas! ---
import com.example.levelupgamer.data.network.TokenManager
import kotlinx.coroutines.Dispatchers

class LoginViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail.asStateFlow()

    fun onCorreoChange(newCorreo: String) {
        _uiState.value = _uiState.value.copy(correo = newCorreo)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun submit(onSuccess: () -> Unit) {
        val state = _uiState.value

        if (state.correo.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(error = "Correo o contraseña vacíos")
            return
        }

        viewModelScope.launch(Dispatchers.IO) { // Usamos IO para la llamada de red
            _uiState.value = state.copy(isLoading = true, error = null)
            delay(500)

            val result = authRepository.login(state.correo, state.password)

            if (result.isSuccess) {
                val loginResponse = result.getOrThrow() // Obtenemos la respuesta

                TokenManager.setToken(loginResponse.token)

                launch(Dispatchers.Main) {
                    _uiState.value = state.copy(isLoading = false, error = null)
                    _userEmail.value = loginResponse.user.correo // Guardamos el email
                    onSuccess() // Llamamos al callback para navegar
                }
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Credenciales incorrectas"
                launch(Dispatchers.Main) {
                    _uiState.value = state.copy(isLoading = false, error = errorMsg)
                }
            }
        }
    }
}
