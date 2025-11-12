package com.example.levelupgamer.ui.screens.producto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelupgamer.viewmodel.ProductoViewModel

@Composable
fun ProductoDetailScreen(navController: NavController, productoId: Int) {
    val colorScheme = MaterialTheme.colorScheme

    val viewModel: ProductoViewModel = viewModel()
    val productos by viewModel.productos.collectAsState()

    val producto = productos.find { it.id == productoId }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (producto == null) {
            Text(
                "Producto no encontrado",
                color = colorScheme.secondary,
                fontFamily = FontFamily.Default,
                style = MaterialTheme.typography.headlineMedium
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.surface)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    producto.nombre,
                    style = MaterialTheme.typography.headlineLarge,
                    fontFamily = FontFamily.Default,
                    color = colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    producto.descripcion,
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = FontFamily.Default,
                    color = colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Precio: $${producto.precio}",
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily.Default,
                    color = colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.secondary,
                        contentColor = colorScheme.onSecondary
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Volver", fontFamily = FontFamily.Default)
                }
            }
        }
    }
}