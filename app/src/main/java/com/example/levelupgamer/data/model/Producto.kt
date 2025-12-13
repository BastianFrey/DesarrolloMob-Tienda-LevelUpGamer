package com.example.levelupgamer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "producto")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @SerializedName("codigo")
    val codigo: String = "",

    @SerializedName("nombre")
    val nombre: String = "",

    @SerializedName("descripcion")
    val descripcion: String = "",

    @SerializedName("precio")
    val precio: Double = 0.0,

    @SerializedName("categoria")
    val categoria: String = "",

    val imagenRes: Int = 0,

    @SerializedName("imagenUrl")
    val imagenUrl: String? = null,

    @SerializedName("activo")
    val activo: Boolean = true
)