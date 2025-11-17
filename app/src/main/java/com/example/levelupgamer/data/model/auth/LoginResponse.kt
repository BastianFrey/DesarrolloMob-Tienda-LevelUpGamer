package com.example.levelupgamer.data.model.auth

data class LoginResponse(
    val token: String,
    val user: UsuarioRespuesta // Usa la data class que creamos arriba
)