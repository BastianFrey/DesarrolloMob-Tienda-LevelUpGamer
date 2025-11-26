package com.example.levelupgamer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelupgamer.data.model.Noticia
import com.example.levelupgamer.viewmodel.NoticiaViewModel

@Composable
fun NoticiasScreen(
    navController: NavController,
    noticiaViewModel: NoticiaViewModel = viewModel()
) {
    val colorScheme = MaterialTheme.colorScheme
    val noticias by noticiaViewModel.allNoticias.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Últimas Noticias",
                style = MaterialTheme.typography.headlineMedium,
                color = colorScheme.secondary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (noticias.isEmpty()) {
                Text("Cargando noticias o no hay noticias disponibles.", color = colorScheme.onSurface)
            }
        }

        items(noticias, key = { it.id }) { noticia ->
            NoticiaCard(
                noticia = noticia,
                colorScheme = colorScheme,
                onClick = {
                }
            )
        }
    }
}

@Composable
fun NoticiaCard(noticia: Noticia, colorScheme: ColorScheme, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (noticia.esDestacada) {
                Text(
                    text = "🔥 DESTACADO",
                    color = colorScheme.error,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            Text(
                text = noticia.titulo,
                fontWeight = FontWeight.Bold,
                color = colorScheme.secondary,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = noticia.resumen,
                color = colorScheme.onBackground,
                fontSize = 14.sp,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Fuente: ${noticia.fuente}",
                color = colorScheme.primary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}