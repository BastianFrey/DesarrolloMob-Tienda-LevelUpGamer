package com.example.levelupgamer.ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelupgamer.viewmodel.UserViewModel

@Composable
fun RegisterScreen(navController: NavController, userViewModel: UserViewModel) {
    val colorScheme = MaterialTheme.colorScheme

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var anioNacimiento by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

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
            .padding(16.dp)
    ) {
        Text(
            "Crear cuenta",
            style = MaterialTheme.typography.titleLarge.copy(
                color = colorScheme.secondary,
                fontFamily = FontFamily.Default
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Nombre ---
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre", color = colorScheme.secondary, fontFamily = FontFamily.Default) },
            textStyle = LocalTextStyle.current.copy(color = colorScheme.onBackground, fontFamily = FontFamily.Default),
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Correo ---
        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo", color = colorScheme.secondary, fontFamily = FontFamily.Default) },
            textStyle = LocalTextStyle.current.copy(color = colorScheme.onBackground, fontFamily = FontFamily.Default),
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Contraseña ---
        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña", color = colorScheme.secondary, fontFamily = FontFamily.Default) },
            textStyle = LocalTextStyle.current.copy(color = colorScheme.onBackground, fontFamily = FontFamily.Default),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Año de Nacimiento ---
        OutlinedTextField(
            value = anioNacimiento,
            onValueChange = { anioNacimiento = it },
            label = { Text("Año de nacimiento", color = colorScheme.secondary, fontFamily = FontFamily.Default) },
            textStyle = LocalTextStyle.current.copy(color = colorScheme.onBackground, fontFamily = FontFamily.Default),
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors
        )

        error?.let {
            Text(it, color = colorScheme.error, modifier = Modifier.padding(top = 8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val correoValido = correo.endsWith("@duocuc.cl") || correo.endsWith("@gmail.com") || correo.endsWith("@admin.cl")
                val anioValido = anioNacimiento.toIntOrNull()?.let { it <= 2005 } ?: false

                error = when {
                    nombre.isBlank() || correo.isBlank() || contrasena.isBlank() || anioNacimiento.isBlank() ->
                        "Todos los campos son obligatorios"
                    !correoValido -> "El correo debe terminar en @duocuc.cl, @gmail.com o @admin.cl"
                    !anioValido -> "Debes ser mayor de 18 años"
                    else -> null
                }

                if (error == null) {
                    userViewModel.register(nombre, correo, contrasena, anioNacimiento.toInt()) { success, message ->
                        if (success) navController.navigate("login")
                        else error = message
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.secondary,
                contentColor = colorScheme.onSecondary
            )
        ) {
            Text("Registrar", color = colorScheme.onSecondary)
        }
    }
}