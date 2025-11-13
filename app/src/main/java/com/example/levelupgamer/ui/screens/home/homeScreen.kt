package com.example.levelupgamer.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelupgamer.R // Asegúrate de importar tu R
import com.example.levelupgamer.data.model.Producto
import com.example.levelupgamer.viewmodel.ProductoViewModel

data class Evento(
    val id: Int,
    val titulo: String,
    val fecha: String,
    val imagenResId: Int
)

val eventosDeEjemplo = listOf(
    Evento(1, "Torneo de Catan", "20 NOV", R.drawable.catan),
    Evento(2, "Showmatch PS5", "25 NOV", R.drawable.ps5),
    Evento(3, "LAN Party", "5 DIC", R.drawable.pc)
)

@Composable
fun HomeScreen(
    navController: NavController,
    productoViewModel: ProductoViewModel = viewModel()
) {
    val colorScheme = MaterialTheme.colorScheme

    val productos by productoViewModel.productosFiltrados.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Header(navController = navController)
        }

        item {
            SeccionEventos(navController = navController, colorScheme = colorScheme)
        }

        item {
            SeccionProductos(
                navController = navController,
                colorScheme = colorScheme,
                productos = productos
            )
        }
    }
}

@Composable
fun Header(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        // Aquí podrías poner tu logo si quisieras
        Text(
            text = "Inicio",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SeccionEventos(navController: NavController, colorScheme: ColorScheme) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Próximos Eventos",
            style = MaterialTheme.typography.titleLarge,
            color = colorScheme.secondary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(eventosDeEjemplo) { evento ->
                EventoCard(evento = evento, colorScheme = colorScheme, onClick = {
                    // Aquí podrías navegar a una pantalla de detalle de evento
                })
            }
        }
    }
}

@Composable
fun EventoCard(evento: Evento, colorScheme: ColorScheme, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .height(180.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = evento.imagenResId),
                contentDescription = evento.titulo,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = evento.titulo,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.secondary,
                    fontSize = 16.sp,
                    maxLines = 1
                )
                Text(
                    text = evento.fecha,
                    color = colorScheme.onSurface,
                    fontSize = 12.sp
                )
            }
        }
    }
}


@Composable
fun SeccionProductos(
    navController: NavController,
    colorScheme: ColorScheme,
    productos: List<Producto>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Productos Destacados",
            style = MaterialTheme.typography.titleLarge,
            color = colorScheme.secondary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(productos.take(10)) { producto ->
                ProductoHomeCard(
                    producto = producto,
                    colorScheme = colorScheme,
                    onClick = {
                        navController.navigate("productoDetalle/${producto.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun ProductoHomeCard(producto: Producto, colorScheme: ColorScheme, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(200.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = productoImage(producto.nombre)),
                contentDescription = producto.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = producto.nombre,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.secondary,
                    fontSize = 14.sp,
                    maxLines = 2
                )
                Text(
                    text = "$${producto.precio}",
                    color = colorScheme.primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

private fun productoImage(nombre: String): Int {
    return when {
        nombre.contains("catan", ignoreCase = true) -> R.drawable.catan
        nombre.contains("carcassonne", ignoreCase = true) -> R.drawable.carcassone
        nombre.contains("xbox", ignoreCase = true) -> R.drawable.mandoxbox
        nombre.contains("hyperx", ignoreCase = true) -> R.drawable.audifonos
        nombre.contains("playstation", ignoreCase = true) || nombre.contains("ps5", ignoreCase = true) -> R.drawable.ps5
        nombre.contains("pc", ignoreCase = true) && nombre.contains("gamer", ignoreCase = true) -> R.drawable.pc
        nombre.contains("silla", ignoreCase = true) -> R.drawable.silla
        nombre.contains("mousepad", ignoreCase = true) -> R.drawable.mousepad
        nombre.contains("mouse", ignoreCase = true) -> R.drawable.mouse
        nombre.contains("polera", ignoreCase = true) -> R.drawable.polera
        else -> R.drawable.ic_launcher_background
    }
}