package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.model.Evento
import com.example.levelupgamer.data.repository.EventoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class EventoViewModel(private val repository: EventoRepository = EventoRepository()) : ViewModel() {

    val allEventos: StateFlow<List<Evento>> = repository.getAllEventos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val eventosDestacados: StateFlow<List<Evento>> = repository.getEventosDestacados(limit = 3)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun getEventoById(id: Int): StateFlow<Evento?> {
        return repository.getEventoById(id)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )
    }
}