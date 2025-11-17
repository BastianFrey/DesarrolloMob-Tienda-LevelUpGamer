package com.example.levelupgamer.data.model.reviews

data class ResenaStatsDTO(
    val promedioCalificacion: Double,
    val totalResenas: Int,
    val resenas: List<ResenaDTO>
)