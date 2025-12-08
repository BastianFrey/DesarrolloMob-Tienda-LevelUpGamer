package com.example.levelupgamer.data.model

data class LoginRequest(
    val correo: String,
    val contrasena: String
)

data class LoginResponse(
    val token: String,
    val rol: String,
    val usuarioId: Long
)