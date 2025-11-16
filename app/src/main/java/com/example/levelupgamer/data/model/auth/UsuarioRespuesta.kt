package com.example.levelupgamer.data.model.auth

data class UsuarioRespuesta(
    val id: Long,
    val run: String,
    val nombre: String,
    val apellidos: String,
    val correo: String,
    val region: String?,
    val comuna: String?,
    val direccion: String?,
    val puntosLevelUp: Int?,
    val codigoReferido: String?,
    val rol: String
)