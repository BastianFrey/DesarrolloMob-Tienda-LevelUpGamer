package com.example.levelupgamer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noticias")
data class Noticia(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val resumen: String,
    val fuente: String,
    val esDestacada: Boolean = false
)