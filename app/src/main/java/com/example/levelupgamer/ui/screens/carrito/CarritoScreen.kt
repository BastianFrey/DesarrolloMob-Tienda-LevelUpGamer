package com.example.levelupgamer.ui.screens.carrito

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelupgamer.viewmodel.CarritoViewModel
import com.example.levelupgamer.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(navController: NavController, userViewModel: UserViewModel) {
    val colorScheme = MaterialTheme.colorScheme

    val carritoViewModel: CarritoViewModel = viewModel()
    val productosCarrito by carritoViewModel.productosCarrito.collectAsState()
    val totalCarrito by carritoViewModel.totalCarrito.collectAsState()
    val currentUser by userViewModel.currentUser.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val tieneDescuento = currentUser?.correo?.endsWith("@duocuc.cl") == true
    val totalConDescuento = if (tieneDescuento) totalCarrito * 0.8 else totalCarrito

    Scaffold(
        modifier = Modifier.background(colorScheme.background),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mi Carrito",
                        color = colorScheme.secondary,
                        fontFamily = FontFamily.Default
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = colorScheme.secondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.background
                )
            )
        },
        bottomBar = {
            if (productosCarrito.isNotEmpty()) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = colorScheme.surface,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (tieneDescuento) {
                            Text(
                                "¡Descuento del 20% aplicado!",
                                color = colorScheme.secondary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Total:",
                                style = MaterialTheme.typography.titleMedium,
                                color = colorScheme.secondary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "$${String.format("%.2f", totalConDescuento)}",
                                style = MaterialTheme.typography.titleMedium,
                                color = colorScheme.secondary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                scope.launch {
                                    snackbarHostState.showSnackbar("¡Compra finalizada con éxito!")
                                }
                                carritoViewModel.limpiarCarrito()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorScheme.secondary,
                                contentColor = colorScheme.onSecondary
                            )
                        ) {
                            Text("Finalizar Compra", fontWeight = FontWeight.Bold)
                        }
                        TextButton(
                            onClick = { carritoViewModel.limpiarCarrito() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Limpiar Carrito",
                                color = colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        if (productosCarrito.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(colorScheme.background)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "Carrito vacío",
                    tint = colorScheme.secondary,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Tu carrito está vacío",
                    style = MaterialTheme.typography.titleMedium,
                    color = colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Agrega algunos productos desde la lista",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onSurface
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(colorScheme.background)
                    .padding(16.dp)
            ) {
                items(productosCarrito) { carritoItem ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(

                            containerColor = colorScheme.surface
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    carritoItem.nombre,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = colorScheme.secondary,
                                    fontFamily = FontFamily.Default
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    carritoItem.descripcion,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colorScheme.onSurface,
                                    fontFamily = FontFamily.Default
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "$${carritoItem.precio} x ${carritoItem.cantidad}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = colorScheme.primary,
                                    fontFamily = FontFamily.Default,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "Subtotal: $${String.format("%.2f", carritoItem.precio * carritoItem.cantidad)}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colorScheme.secondary
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                IconButton(
                                    onClick = {
                                        carritoViewModel.actualizarCantidad(carritoItem, carritoItem.cantidad + 1)
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = "Aumentar",
                                        tint = colorScheme.secondary
                                    )
                                }
                                Text(
                                    carritoItem.cantidad.toString(),
                                    color = colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                                IconButton(
                                    onClick = {
                                        carritoViewModel.actualizarCantidad(carritoItem, carritoItem.cantidad - 1)
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Disminuir",
                                        tint = colorScheme.primary
                                    )
                                }
                            }

                            IconButton(
                                onClick = { carritoViewModel.eliminarDelCarrito(carritoItem) }
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}