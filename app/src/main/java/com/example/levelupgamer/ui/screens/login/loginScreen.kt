package com.example.levelupgamer.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment // ¡Importante!
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
// --- ¡Importaciones cambiadas! ---
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupgamer.viewmodel.LoginViewModel // Ya no usa UserViewModel
// ---------------------------------

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {
    val colorScheme = MaterialTheme.colorScheme

    val uiState by loginViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                value = uiState.correo,
                onValueChange = loginViewModel::onCorreoChange,
                label = { Text("Correo", color = colorScheme.secondary, fontFamily = FontFamily.Default) },
                textStyle = LocalTextStyle.current.copy(color = colorScheme.onBackground, fontFamily = FontFamily.Default),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = colorScheme.onBackground,
                    unfocusedTextColor = colorScheme.onBackground,
                    focusedBorderColor = colorScheme.secondary,
                    unfocusedBorderColor = colorScheme.onSurface,
                    cursorColor = colorScheme.primary
                ),
                isError = uiState.error != null
            )

            Spacer(modifier = Modifier.height(16.dp))

            // CONTRASEÑA
            OutlinedTextField(
                value = uiState.password,
                onValueChange = loginViewModel::onPasswordChange,
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
                ),
                isError = uiState.error != null
            )

            uiState.error?.let {
                Text(
                    it,
                    color = colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de entrar
            Button(
                onClick = {
                    loginViewModel.submit {
                        navController.navigate("home")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.secondary,
                    contentColor = colorScheme.onSecondary
                ),
                enabled = !uiState.isLoading
            ) {
                Text("Entrar", color = colorScheme.onSecondary)
            }
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = colorScheme.secondary
            )
        }
    }
}