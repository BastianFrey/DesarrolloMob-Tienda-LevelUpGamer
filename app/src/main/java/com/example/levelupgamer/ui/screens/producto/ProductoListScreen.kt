package com.example.levelupgamer.ui.screens.producto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelupgamer.viewmodel.CarritoViewModel
import com.example.levelupgamer.viewmodel.ProductoViewModel
import com.example.levelupgamer.viewmodel.UserViewModel

@Composable
fun ProductoListScreen(navController: NavController, userViewModel: UserViewModel) {
    val productoViewModel: ProductoViewModel = viewModel()
    val carritoViewModel: CarritoViewModel = viewModel()
    val productos by productoViewModel.productos.collectAsState()
    val currentUser by userViewModel.currentUser.collectAsState()

    val isAdmin = currentUser?.correo?.endsWith("@admin.cl") == true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(productos) { producto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            producto.nombre,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF39FF14),
                            fontFamily = FontFamily.Default
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            producto.descripcion,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFD3D3D3),
                            fontFamily = FontFamily.Default
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "$${producto.precio}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF39FF14),
                            fontFamily = FontFamily.Default
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { carritoViewModel.agregarAlCarrito(producto) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1E90FF),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Agregar al Carrito")
                        }

                        TextButton(
                            onClick = { navController.navigate("productoDetalle/${producto.id}") },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Ver Detalles", color = Color(0xFF39FF14))
                        }
                    }
                }
            }
        }

        if (isAdmin) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("agregarProducto") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF39FF14),
                    contentColor = Color.Black
                )
            ) {
                Text("Agregar Producto")
            }
        }
    }
}
