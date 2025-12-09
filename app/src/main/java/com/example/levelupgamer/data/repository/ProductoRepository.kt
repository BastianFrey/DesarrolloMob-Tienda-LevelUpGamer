package com.example.levelupgamer.data.repository

import android.util.Log
import com.example.levelupgamer.data.dao.ProductoDao
import com.example.levelupgamer.data.model.Producto
import com.example.levelupgamer.data.network.RetrofitClient
import kotlinx.coroutines.flow.Flow

class ProductoRepository(private val dao: ProductoDao) {

    private val api = RetrofitClient.instance

    suspend fun obtenerProductosDeApi(): List<Producto> {
        return try {
            val response = api.obtenerProductos()

            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("API_ERROR", "Error del servidor: ${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Fallo de conexión: ${e.message}")
            emptyList()
        }
    }

    suspend fun insert(producto: Producto) = dao.insert(producto)

    fun getAllProductosLocal(): Flow<List<Producto>> = dao.getAllProductos()
}