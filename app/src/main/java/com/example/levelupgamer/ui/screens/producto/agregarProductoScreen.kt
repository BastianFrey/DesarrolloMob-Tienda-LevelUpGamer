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
import com.example.levelupgamer.viewmodel.UserViewModel

private val categoriasParaAgregar = listOf(
    "Juegos de Mesa",
    "Accesorios",
    "Consolas",
    "Computadores Gamers",
    "Sillas Gamers",
    "Mouse",
    "Mousepad",
    "Poleras gamers"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarProductoScreen(
    navController: NavController,
    vm: ProductoViewModel = viewModel(),
    userViewModel: UserViewModel
) {
    val colorScheme = MaterialTheme.colorScheme

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    var categoriaSeleccionada by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = colorScheme.onBackground,
        unfocusedTextColor = colorScheme.onBackground,
        focusedBorderColor = colorScheme.secondary,
        unfocusedBorderColor = colorScheme.onSurface,
        cursorColor = colorScheme.primary
    )

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

        // Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre", color = colorScheme.secondary) },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors
        )

        // Descripción
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción", color = colorScheme.secondary) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = textFieldColors
        )

        // Precio
        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio", color = colorScheme.secondary) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = textFieldColors
        )

        OutlinedTextField(
            value = imagenUrl,
            onValueChange = { imagenUrl = it },
            label = { Text("URL de Imagen (Opcional)", color = colorScheme.secondary) },
            placeholder = { Text("https://ejemplo.com/foto.jpg") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = textFieldColors
        )

        // MENÚ DE CATEGORÍA
        Spacer(modifier = Modifier.height(16.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = categoriaSeleccionada,
                onValueChange = {},
                readOnly = true,
                label = { Text("Categoría", color = colorScheme.secondary) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = textFieldColors,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(colorScheme.surface)
            ) {
                categoriasParaAgregar.forEach { categoria ->
                    DropdownMenuItem(
                        text = { Text(categoria) },
                        onClick = {
                            categoriaSeleccionada = categoria
                            expanded = false
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = colorScheme.onSurface
                        )
                    )
                }
            }
        }

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

                val token = userViewModel.authToken

                if (token == null) {
                    error = "Error de sesión: No eres administrador o la sesión expiró."
                    return@Button
                }

                if (nombre.isBlank() || descripcion.isBlank() || precioDouble == null || categoriaSeleccionada.isBlank()) {
                    error = "Completa todos los campos obligatorios"
                    return@Button
                }

                val categoriaBackend = categoriaSeleccionada.uppercase().replace(" ", "_")

                vm.agregarProducto(
                    token = token,
                    nombre = nombre,
                    descripcion = descripcion,
                    precio = precioDouble,
                    categoria = categoriaBackend,
                    imagenUrl = imagenUrl
                )

                navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.secondary,
                contentColor = colorScheme.onSecondary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Producto")
        }
    }
}