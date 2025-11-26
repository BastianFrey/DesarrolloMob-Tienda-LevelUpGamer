package com.example.levelupgamer.data.repository

import com.example.levelupgamer.R
import com.example.levelupgamer.data.model.Evento
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EventoRepository {

    private val eventosDeEjemplo = listOf(
        Evento(1, "Torneo de Catan", "20 NOV", R.drawable.catan),
        Evento(2, "FIFA PS5", "25 NOV", R.drawable.ps5),
        Evento(3, "CS GO Party", "5 DIC", R.drawable.pc),
        Evento(4, "Noche de Juegos de Mesa", "10 DIC", R.drawable.carcassone),
        Evento(5, "Streaming Benéfico", "18 DIC", R.drawable.logo)
    )

    fun getAllEventos(): Flow<List<Evento>> = flow {
        emit(eventosDeEjemplo)
    }

    fun getEventoById(id: Int): Flow<Evento?> = flow {
        emit(eventosDeEjemplo.find { it.id == id })
    }

    fun getEventosDestacados(limit: Int = 3): Flow<List<Evento>> = flow {
        emit(eventosDeEjemplo.take(limit))
    }
}