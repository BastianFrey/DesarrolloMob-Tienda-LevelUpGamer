package com.example.levelupgamer.ui.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun WelcomeScreen(navController: NavController) {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "¡Bienvenido a LevelUP-Gamer!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorScheme.secondary,
            fontFamily = FontFamily.Default
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tu tienda gamer favorita",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = colorScheme.secondary,
            fontFamily = FontFamily.Default
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("login") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.secondary,
                contentColor = colorScheme.onSecondary
            )
        ) {
            Text(
                "Iniciar Sesión",
                color = colorScheme.onSecondary,
                fontFamily = FontFamily.Default
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("register") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.secondary,
                contentColor = colorScheme.onSecondary
            )
        ) {
            Text(
                "Registrarse",
                color = colorScheme.onSecondary,
                fontFamily = FontFamily.Default
            )
        }
    }
}