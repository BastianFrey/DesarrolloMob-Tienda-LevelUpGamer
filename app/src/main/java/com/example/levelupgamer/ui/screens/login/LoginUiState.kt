package com.example.levelupgamer.ui.screens.login

data class LoginUiState(
    val correo: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)