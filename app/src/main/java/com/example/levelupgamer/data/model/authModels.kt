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

data class RegisterRequest(
    val nombre: String,
    val apellidos: String,
    val correo: String,
    val contrasena: String,
    val fechaNacimiento: String,

    val run: String,
    val region: String,
    val comuna: String,
    val direccion: String,

    val codigoReferido: String? = null
)