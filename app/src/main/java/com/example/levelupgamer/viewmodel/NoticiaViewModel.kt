package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.model.Noticia
import com.example.levelupgamer.data.repository.NoticiaRepository // Asegúrate de que esta importación sea correcta
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class NoticiaViewModel : ViewModel() {

    private val repository = NoticiaRepository()

    val allNoticias: StateFlow<List<Noticia>> = repository.getAllNoticias()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val noticiasDestacadas: StateFlow<List<Noticia>> = repository.getNoticiasDestacadas(limit = 2)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun getNoticiaById(id: Int): StateFlow<Noticia?> {
        return repository.getNoticiaById(id)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )
    }
}