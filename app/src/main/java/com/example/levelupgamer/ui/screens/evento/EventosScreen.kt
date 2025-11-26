package com.example.levelupgamer.ui.screens.evento

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelupgamer.R
import com.example.levelupgamer.data.model.Evento
import com.example.levelupgamer.viewmodel.EventoViewModel

@Composable
fun EventosScreen(
    navController: NavController,
    eventoViewModel: EventoViewModel = viewModel()
) {
    val colorScheme = MaterialTheme.colorScheme
    val eventos by eventoViewModel.allEventos.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Todos los Eventos",
                style = MaterialTheme.typography.headlineMedium,
                color = colorScheme.secondary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (eventos.isEmpty()) {
                Text("Cargando eventos o no hay eventos disponibles.", color = colorScheme.onSurface)
            }
        }

        items(eventos, key = { it.id }) { evento ->
            EventoCardCompleta(
                evento = evento,
                colorScheme = colorScheme,
                onClick = {
                }
            )
        }
    }
}

@Composable
fun EventoCardCompleta(evento: Evento, colorScheme: ColorScheme, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {

            Image(
                painter = painterResource(id = eventoImage(evento.imagenNombre)),
                contentDescription = evento.titulo,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = evento.titulo,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.secondary,
                    fontSize = 18.sp
                )
                Text(
                    text = "Fecha: ${evento.fecha}",
                    color = colorScheme.onSurface,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = "¡Cupos Limitados! Regístrate ahora.",
                    color = colorScheme.error,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

private fun eventoImage(nombre: String): Int {
    return when (nombre.lowercase()) {
        "catan" -> R.drawable.catan
        "carcassone" -> R.drawable.carcassone
        "ps5" -> R.drawable.ps5
        "pc" -> R.drawable.pc
        "logo" -> R.drawable.logo
        else -> R.drawable.ic_launcher_background
    }
}