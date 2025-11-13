package com.example.levelupgamer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.database.ResenaDatabase
import com.example.levelupgamer.data.model.Resena
import com.example.levelupgamer.data.repository.ResenaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ResenaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ResenaRepository

    init {
        val dao = ResenaDatabase.getDatabase(application).resenaDao()
        repository = ResenaRepository(dao)
    }

    fun getResenasForProducto(productoId: Int): Flow<List<Resena>> {
        return repository.getResenasForProducto(productoId)
    }

    fun promedio(productoId: Int): Flow<Double?> {
        return repository.promedio(productoId)
    }

    fun agregarResena(productoId: Int, userId: Int, userName: String, calificacion: Int, comentario: String) {
        if (calificacion !in 1..5) return

        viewModelScope.launch {
            val nuevaResena = Resena(
                productoId = productoId,
                userId = userId,
                nombreUsuario = userName,
                calificacion = calificacion,
                comentario = comentario
            )
            repository.insert(nuevaResena)
        }
    }
}