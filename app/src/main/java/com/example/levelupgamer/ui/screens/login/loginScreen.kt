package com.example.levelupgamer.ui.screens.login

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
fun LoginScreen(navController: NavController, userViewModel: UserViewModel) {
    val colorScheme = MaterialTheme.colorScheme

    var nombre by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            "Iniciar sesión",
            style = MaterialTheme.typography.titleLarge.copy(
                color = colorScheme.secondary,
                fontFamily = FontFamily.Default
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre", color = colorScheme.secondary, fontFamily = FontFamily.Default) },
            textStyle = LocalTextStyle.current.copy(color = colorScheme.onBackground, fontFamily = FontFamily.Default),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colorScheme.onBackground,
                unfocusedTextColor = colorScheme.onBackground,
                focusedBorderColor = colorScheme.secondary,
                unfocusedBorderColor = colorScheme.onSurface,
                cursorColor = colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña", color = colorScheme.secondary, fontFamily = FontFamily.Default) },
            textStyle = LocalTextStyle.current.copy(color = colorScheme.onBackground, fontFamily = FontFamily.Default),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colorScheme.onBackground,
                unfocusedTextColor = colorScheme.onBackground,
                focusedBorderColor = colorScheme.secondary,
                unfocusedBorderColor = colorScheme.onSurface,
                cursorColor = colorScheme.primary
            )
        )

        error?.let {
            Text(
                it,
                color = colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (nombre.isBlank() || contrasena.isBlank()) {
                    error = "Todos los campos son obligatorios"
                } else {
                    userViewModel.login(nombre, contrasena) { success ->
                        if (success) navController.navigate("home")
                        else error = "Nombre o contraseña incorrecta"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.secondary,
                contentColor = colorScheme.onSecondary
            )
        ) {
            Text("Entrar", color = colorScheme.onSecondary)
        }
    }
}