package com.example.levelupgamer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resenas")
data class Resena(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productoId: Int,
    val userId: Int,
    val nombreUsuario: String,
    val calificacion: Int,
    val comentario: String,
    val fecha: Long = System.currentTimeMillis()
)