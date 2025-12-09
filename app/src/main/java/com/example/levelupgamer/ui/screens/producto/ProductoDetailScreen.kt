package com.example.levelupgamer.ui.screens.producto

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.levelupgamer.R // Asegúrate de importar tu R para las imágenes placeholder
import com.example.levelupgamer.data.model.Resena
import com.example.levelupgamer.viewmodel.CarritoViewModel
import com.example.levelupgamer.viewmodel.ProductoViewModel
import com.example.levelupgamer.viewmodel.ResenaViewModel
import com.example.levelupgamer.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoDetailScreen(
    navController: NavController,
    productoId: Long, // 1. CAMBIO: ID recibido como Long (Backend)
    userViewModel: UserViewModel,
    carritoViewModel: CarritoViewModel = viewModel()
) {
    val colorScheme = MaterialTheme.colorScheme
    val context = LocalContext.current

    val productoViewModel: ProductoViewModel = viewModel()
    val resenaViewModel: ResenaViewModel = viewModel()

    val currentUser by userViewModel.currentUser.collectAsState()

    // 2. CAMBIO: Observamos la lista global del Backend y buscamos el producto
    val listaProductos by productoViewModel.productosFiltrados.collectAsState()
    val producto = listaProductos.find { it.id.toLong() == productoId }

    // 3. CAMBIO: Convertimos a Int SOLO para las reseñas (que siguen siendo locales)
    val productoIdInt = productoId.toInt()

    val resenas by resenaViewModel.getResenasForProducto(productoIdInt).collectAsState(initial = emptyList())
    val promedioCalificacionNullable by resenaViewModel.promedio(productoIdInt).collectAsState(initial = null)
    val promedioCalificacion = promedioCalificacionNullable ?: 0.0

    var showReviewModal by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(producto?.nombre ?: "Detalle del Producto", color = colorScheme.secondary) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = colorScheme.secondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.background
                )
            )
        },
        containerColor = colorScheme.background
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (producto == null) {
                // Si la lista del backend aún no carga o el ID no existe
                CircularProgressIndicator(color = colorScheme.primary)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        // DETALLE DE PRODUCTO
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // 4. NUEVO: Imagen desde URL (Backend) usando Coil
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(producto.imagenUrl) // URL del backend
                                        .crossfade(true)
                                        .error(producto.imagenRes) // Fallback a imagen local si falla URL
                                        .placeholder(R.drawable.logo) // Placeholder mientras carga
                                        .build(),
                                    contentDescription = producto.nombre,
                                    modifier = Modifier
                                        .height(200.dp)
                                        .fillMaxWidth(),
                                    contentScale = ContentScale.Fit
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    producto.nombre,
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = colorScheme.secondary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    producto.descripcion,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = colorScheme.onBackground
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    "Precio: $${producto.precio}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = colorScheme.secondary
                                )

                                Spacer(modifier = Modifier.height(12.dp))
                                RatingDisplay(promedio = promedioCalificacion, totalResenas = resenas.size)
                            }
                        }

                        // BOTONES DE ACCIÓN
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    carritoViewModel.agregarAlCarrito(producto)
                                    scope.launch {
                                        val result = snackbarHostState.showSnackbar(
                                            message = "Se agregó ${producto.nombre} al carrito",
                                            actionLabel = "Ver Carrito",
                                            duration = SnackbarDuration.Short
                                        )
                                        if (result == SnackbarResult.ActionPerformed) {
                                            navController.navigate("carrito")
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorScheme.primary,
                                    contentColor = colorScheme.onPrimary
                                ),
                                modifier = Modifier.weight(1f).padding(end = 8.dp)
                            ) {
                                Text("Agregar al Carrito")
                            }

                            Button(
                                onClick = {
                                    if (currentUser != null) {
                                        showReviewModal = true
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Debes iniciar sesión para dejar una reseña.",
                                            Toast.LENGTH_LONG).show()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorScheme.secondary,
                                    contentColor = colorScheme.onSecondary
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Dejar Reseña")
                            }
                        }
                    }

                    // RESEÑAS
                    item {
                        Text(
                            "Reseñas de la Comunidad (${resenas.size})",
                            style = MaterialTheme.typography.titleLarge,
                            color = colorScheme.primary,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }

                    items(resenas) { resena ->
                        ResenaCard(resena = resena, colorScheme = colorScheme)
                    }
                }
            }
        }
    }

    if (showReviewModal && currentUser != null && producto != null) {
        val user = currentUser!!
        ReviewModal(
            productoId = productoId, // Pasamos Long
            userId = user.id,
            userName = user.nombre,
            resenaViewModel = resenaViewModel,
            onDismiss = { showReviewModal = false }
        )
    }
}

@Composable
fun ResenaCard(resena: Resena, colorScheme: ColorScheme) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    resena.nombreUsuario,
                    style = MaterialTheme.typography.titleMedium,
                    color = colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
                RatingStars(calificacion = resena.calificacion)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                resena.comentario,
                style = MaterialTheme.typography.bodyMedium,
                color = colorScheme.onBackground
            )
            Text(
                "Fecha: ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(resena.fecha))}",
                fontSize = 12.sp,
                color = colorScheme.onSurface
            )
        }
    }
}

@Composable
fun RatingStars(calificacion: Int) {
    val colorScheme = MaterialTheme.colorScheme
    Row {
        for (i in 1..5) {
            Icon(
                Icons.Default.Star,
                contentDescription = null,
                tint = if (i <= calificacion) Color(0xFFFFC107) else colorScheme.onSurface.copy(alpha = 0.5f),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun RatingDisplay(promedio: Double, totalResenas: Int) {
    val colorScheme = MaterialTheme.colorScheme
    val starsToShow = promedio.toInt()
    Row(verticalAlignment = Alignment.CenterVertically) {
        RatingStars(calificacion = starsToShow)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            String.format("%.1f", promedio),
            color = Color(0xFFFFC107),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            "(${totalResenas} Reseñas)",
            color = colorScheme.onSurface,
            fontSize = 12.sp
        )
    }
}

@Composable
fun ReviewModal(
    productoId: Long, // Recibe Long
    userId: Int,
    userName: String,
    resenaViewModel: ResenaViewModel,
    onDismiss: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    var calificacion by remember { mutableIntStateOf(5) }
    var comentario by remember { mutableStateOf("") }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = colorScheme.onBackground,
        unfocusedTextColor = colorScheme.onBackground,
        focusedBorderColor = colorScheme.secondary,
        unfocusedBorderColor = colorScheme.onSurface,
        cursorColor = colorScheme.primary,
        focusedLabelColor = colorScheme.secondary,
        unfocusedLabelColor = colorScheme.onSurface,
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Deja tu Reseña", color = colorScheme.secondary, style = MaterialTheme.typography.titleMedium) },
        text = {
            Column {
                Text("Usuario: $userName", color = colorScheme.onBackground)
                Spacer(modifier = Modifier.height(16.dp))

                Text("Calificación:", color = colorScheme.onSurface)
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    for (i in 1..5) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "Estrella $i",
                            tint = if (i <= calificacion) Color(0xFFFFC107) else colorScheme.onSurface.copy(alpha = 0.5f),
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { calificacion = i }
                        )
                    }
                }

                OutlinedTextField(
                    value = comentario,
                    onValueChange = { comentario = it },
                    label = { Text("Comentario", color = colorScheme.secondary) },
                    textStyle = LocalTextStyle.current.copy(color = colorScheme.onBackground),
                    modifier = Modifier.fillMaxWidth().height(100.dp).padding(top = 8.dp),
                    colors = textFieldColors
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (comentario.isNotBlank()) {
                        // Cast a INT solo para guardar en BD Local
                        resenaViewModel.agregarResena(
                            productoId = productoId.toInt(),
                            userId = userId,
                            userName = userName,
                            calificacion = calificacion,
                            comentario = comentario
                        )
                        onDismiss()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorScheme.secondary)
            ) {
                Text("Enviar Reseña", color = colorScheme.onSecondary)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = colorScheme.error)
            ) {
                Text("Cancelar", color = colorScheme.onError)
            }
        },
        containerColor = colorScheme.surface
    )
}