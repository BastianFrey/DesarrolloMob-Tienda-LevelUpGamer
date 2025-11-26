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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelupgamer.R
// ⬇️ Nuevas importaciones de Modelos y ViewModels
import com.example.levelupgamer.data.model.Evento
import com.example.levelupgamer.data.model.Noticia
import com.example.levelupgamer.data.model.Producto
import com.example.levelupgamer.viewmodel.EventoViewModel
import com.example.levelupgamer.viewmodel.NoticiaViewModel
// ⬆️ Fin nuevas importaciones
import com.example.levelupgamer.viewmodel.ProductoViewModel
import com.example.levelupgamer.viewmodel.UserViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    productoViewModel: ProductoViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(),
    eventoViewModel: EventoViewModel = viewModel(),
    noticiaViewModel: NoticiaViewModel = viewModel()
) {
    val colorScheme = MaterialTheme.colorScheme

    val productos by productoViewModel.productosFiltrados.collectAsState()

    val eventosDestacados by eventoViewModel.eventosDestacados.collectAsState()
    val noticiasDestacadas by noticiaViewModel.noticiasDestacadas.collectAsState()

    val currentUser by userViewModel.currentUser.collectAsState()
    val nombreUsuario = currentUser?.nombre ?: "Gamer"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Header(navController = navController, nombreUsuario = nombreUsuario)
        }

        item {
            SeccionEventos(
                navController = navController,
                colorScheme = colorScheme,
                eventos = eventosDestacados
            )
        }

        item {
            SeccionNoticias(
                navController = navController,
                colorScheme = colorScheme,
                noticias = noticiasDestacadas
            )
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
fun Header(navController: NavController, nombreUsuario: String) {

    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo LevelUp Gamer",
            modifier = Modifier.height(60.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Bienvenido, $nombreUsuario",
            style = MaterialTheme.typography.titleMedium,
            color = colorScheme.secondary
        )
    }
}

@Composable
fun SeccionEventos(navController: NavController, colorScheme: ColorScheme, eventos: List<Evento>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Próximos Eventos",
                style = MaterialTheme.typography.titleLarge,
                color = colorScheme.secondary,
                fontWeight = FontWeight.Bold,
            )
            if (eventos.isNotEmpty()) {
                Text(
                    text = "Ver todos >",
                    color = colorScheme.primary,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { navController.navigate("eventos") } // NAVEGACIÓN
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (eventos.isEmpty()) {
            Text(
                text = "No hay eventos próximos.",
                modifier = Modifier.padding(horizontal = 16.dp),
                color = colorScheme.onSurface
            )
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(eventos) { evento ->
                    EventoCard(evento = evento, colorScheme = colorScheme, onClick = {
                    })
                }
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
                // ⬇️ CORRECCIÓN CLAVE: Usamos la función de mapeo para obtener el ID (Int)
                painter = painterResource(id = eventoImage(evento.imagenNombre)),
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
fun SeccionNoticias(navController: NavController, colorScheme: ColorScheme, noticias: List<Noticia>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Últimas Noticias",
                style = MaterialTheme.typography.titleLarge,
                color = colorScheme.secondary,
                fontWeight = FontWeight.Bold,
            )
            if (noticias.isNotEmpty()) {
                Text(
                    text = "Ver todas >",
                    color = colorScheme.primary,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { navController.navigate("noticias") }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (noticias.isEmpty()) {
            Text(
                text = "No hay noticias destacadas.",
                modifier = Modifier.padding(horizontal = 16.dp),
                color = colorScheme.onSurface
            )
        } else {
            Column(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                noticias.forEach { noticia ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate("noticias") },
                        colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = noticia.titulo,
                                fontWeight = FontWeight.Bold,
                                color = colorScheme.secondary,
                                fontSize = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Fuente: ${noticia.fuente}",
                                color = colorScheme.onSurface,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
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