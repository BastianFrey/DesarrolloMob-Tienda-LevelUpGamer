package com.example.levelupgamer.data.repository

import com.example.levelupgamer.data.model.Noticia
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NoticiaRepository {

    private val noticiasDeEjemplo = listOf(
        Noticia(1, "Lanzamiento: Nuevo Mouse Gamer HyperX Pro", "Análisis completo de la última novedad en periféricos de alta gama.", "Hardware Blog", true),
        Noticia(2, "Ofertas Flash de la Semana", "Hasta 50% de descuento en consolas seleccionadas, ¡solo por 48 horas!", "LevelUp Gamer", true),
        Noticia(3, "Guía Rápida: Cómo Elegir tu Silla Gamer Ideal", "Consejos para cuidar tu espalda y mejorar tu setup de juego.", "Comunidad", false),
        Noticia(4, "Parche de Balance para el Juego del Año", "Detalles de los cambios que afectan el meta actual.", "Noticias Gaming", false),
        Noticia(5, "Los 5 Mejores Juegos Indie de 2024", "Una selección de gemas ocultas que no te puedes perder.", "Crítica Gaming", false)
    )

    fun getAllNoticias(): Flow<List<Noticia>> = flow {
        emit(noticiasDeEjemplo)
    }

    fun getNoticiasDestacadas(limit: Int = 2): Flow<List<Noticia>> = flow {
        emit(noticiasDeEjemplo.filter { it.esDestacada }.take(limit))
    }

    fun getNoticiaById(id: Int): Flow<Noticia?> = flow {
        emit(noticiasDeEjemplo.find { it.id == id })
    }
}