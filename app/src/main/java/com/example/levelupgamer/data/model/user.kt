package com.example.levelupgamer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val anioNacimiento: Int,
    val puntosLevelUp: Int,
    val nivelGamer: Int,
    val rol: String = "usuario"
)
