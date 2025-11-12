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
fun AgregarProductoScreen(navController: NavController, vm: ProductoViewModel = viewModel()) {
    val colorScheme = MaterialTheme.colorScheme

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Agregar Producto",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = colorScheme.secondary,
                fontFamily = FontFamily.Default
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Nombre ---
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre", color = colorScheme.secondary) },
            textStyle = LocalTextStyle.current.copy(color = colorScheme.onBackground),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colorScheme.onBackground,
                unfocusedTextColor = colorScheme.onBackground,
                focusedBorderColor = colorScheme.secondary, // Borde activo: NeonGreen
                unfocusedBorderColor = colorScheme.onSurface, // Borde inactivo: LightGrey
                cursorColor = colorScheme.primary // Cursor: ElectricBlue
            )
        )

        // --- Descripción ---
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción", color = colorScheme.secondary) },
            textStyle = LocalTextStyle.current.copy(color = colorScheme.onBackground),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colorScheme.onBackground,
                unfocusedTextColor = colorScheme.onBackground,
                focusedBorderColor = colorScheme.secondary,
                unfocusedBorderColor = colorScheme.onSurface,
                cursorColor = colorScheme.primary
            )
        )

        // --- Precio ---
        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio", color = colorScheme.secondary) },
            textStyle = LocalTextStyle.current.copy(color = colorScheme.onBackground),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colorScheme.onBackground,
                unfocusedTextColor = colorScheme.onBackground,
                focusedBorderColor = colorScheme.secondary,
                unfocusedBorderColor = colorScheme.onSurface,
                cursorColor = colorScheme.primary
            )
        )

        if (error.isNotEmpty()) {
            Text(
                error,
                color = colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val precioDouble = precio.toDoubleOrNull()
                if (nombre.isBlank() || descripcion.isBlank() || precioDouble == null) {
                    error = "Completa todos los campos correctamente"
                    return@Button
                }
                vm.agregarProducto(nombre, descripcion, precioDouble)
                navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.secondary,
                contentColor = colorScheme.onSecondary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar")
        }
    }
}