package com.example.levelupgamer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    val colorScheme = MaterialTheme.colorScheme
    var currentScreen by remember { mutableStateOf("welcome") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Título de la app
        Text(
            text = "LevelUP-Gamer",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        TabRow(
            selectedTabIndex = when (currentScreen) {
                "welcome" -> 0
                "login" -> 1
                "register" -> 2
                else -> 0
            },
            containerColor = colorScheme.background,
            contentColor = colorScheme.onBackground,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[when (currentScreen) {
                        "welcome" -> 0
                        "login" -> 1
                        "register" -> 2
                        else -> 0
                    }]),
                    color = colorScheme.secondary
                )
            }
        ) {
            Tab(
                selected = currentScreen == "welcome",
                onClick = { currentScreen = "welcome" },
                selectedContentColor = colorScheme.secondary,
                unselectedContentColor = colorScheme.onBackground,
                text = { Text("Inicio") }
            )
            Tab(
                selected = currentScreen == "login",
                onClick = { currentScreen = "login" },
                selectedContentColor = colorScheme.secondary,
                unselectedContentColor = colorScheme.onBackground,
                text = { Text("Iniciar Sesión") }
            )
            Tab(
                selected = currentScreen == "register",
                onClick = { currentScreen = "register" },
                selectedContentColor = colorScheme.secondary,
                unselectedContentColor = colorScheme.onBackground,
                text = { Text("Registrarse") }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(modifier = Modifier.padding(horizontal = 8.dp)) {
            when (currentScreen) {
                "welcome" -> WelcomeScreenInternal()
                "login" -> LoginScreenInternal(navController)
                "register" -> RegisterScreenInternal(navController)
            }
        }
    }
}


@Composable
fun WelcomeScreenInternal() {
    val colorScheme = MaterialTheme.colorScheme
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "¡Bienvenido a LevelUP-Gamer!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorScheme.secondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Tu tienda de productos gamer favorita",
            fontSize = 16.sp,
            color = colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Encuentra los mejores productos gaming al mejor precio",
            fontSize = 14.sp,
            color = colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LoginScreenInternal(navController: NavController) {
    val colorScheme = MaterialTheme.colorScheme
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = colorScheme.onBackground,
        unfocusedTextColor = colorScheme.onBackground,
        focusedBorderColor = colorScheme.secondary,
        unfocusedBorderColor = colorScheme.onSurface,
        cursorColor = colorScheme.primary,
        focusedLabelColor = colorScheme.secondary,
        unfocusedLabelColor = colorScheme.onSurface,
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Iniciar Sesión",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = textFieldColors
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            visualTransformation = PasswordVisualTransformation(),
            colors = textFieldColors
        )

        Button(
            onClick = { /* Lógica de inicio de sesión */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.secondary,
                contentColor = colorScheme.onSecondary
            )
        ) {
            Text("Iniciar Sesión")
        }
    }
}

@Composable
fun RegisterScreenInternal(navController: NavController) {
    val colorScheme = MaterialTheme.colorScheme
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = colorScheme.onBackground,
        unfocusedTextColor = colorScheme.onBackground,
        focusedBorderColor = colorScheme.secondary,
        unfocusedBorderColor = colorScheme.onSurface,
        cursorColor = colorScheme.primary,
        focusedLabelColor = colorScheme.secondary,
        unfocusedLabelColor = colorScheme.onSurface,
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Crear Cuenta",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre completo") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = textFieldColors
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = textFieldColors
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            visualTransformation = PasswordVisualTransformation(),
            colors = textFieldColors
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar contraseña") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            visualTransformation = PasswordVisualTransformation(),
            colors = textFieldColors
        )

        Button(
            onClick = { /* Lógica de registro */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.secondary,
                contentColor = colorScheme.onSecondary
            )
        ) {
            Text("Registrarse")
        }
    }
}