package com.example.levelupgamer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carrito")
data class CarritoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productoId: Long,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val cantidad: Int = 1
)