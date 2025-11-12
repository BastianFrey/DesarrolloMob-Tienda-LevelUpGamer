package com.example.levelupgamer.data.repository

import com.example.levelupgamer.data.dao.ResenaDao
import com.example.levelupgamer.data.model.Resena
import kotlinx.coroutines.flow.Flow

class ResenaRepository(private val resenaDao: ResenaDao) {

    fun getResenasForProducto(productoId: Int): Flow<List<Resena>> =
        resenaDao.getResenasForProducto(productoId)

    suspend fun insert(resena: Resena) = resenaDao.insert(resena)

    fun getAverageRating(productoId: Int): Flow<Double> = resenaDao.getAverageRating(productoId)
}