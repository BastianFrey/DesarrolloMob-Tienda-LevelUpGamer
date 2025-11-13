package com.example.levelupgamer.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.levelupgamer.data.model.Resena
import kotlinx.coroutines.flow.Flow

@Dao
interface ResenaDao {

    @Query("SELECT * FROM resenas WHERE productoId = :productoId ORDER BY fecha DESC")
    fun getResenasForProducto(productoId: Int): Flow<List<Resena>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resena: Resena)


    @Query("SELECT AVG(calificacion) FROM resenas WHERE productoId = :productoId")
    fun promedio(productoId: Int): Flow<Double?>
}