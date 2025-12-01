package com.example.levelupgamer.data.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val usuarioId: Long,
    val rol: String
)