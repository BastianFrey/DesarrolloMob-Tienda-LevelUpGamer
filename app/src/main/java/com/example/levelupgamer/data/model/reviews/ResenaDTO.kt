package com.example.levelupgamer.data.model.reviews


data class ResenaDTO(
    val id: Long,
    val calificacion: Int,
    val comentario: String?,
    val nombreUsuario: String,
    val fecha: String
)