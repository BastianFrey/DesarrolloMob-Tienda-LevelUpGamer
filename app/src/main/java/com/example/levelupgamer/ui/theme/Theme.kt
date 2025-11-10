package com.example.levelupgamer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = ElectricBlue,          // Azul eléctrico
    secondary = NeonGreen,           // Verde neón
    tertiary = DeepPurple,           // Púrpura intenso
    background = JetBlack,           // Fondo principal
    surface = CharcoalGrey,          // Superficies (cards, panels)
    onPrimary = WhiteText,           // Texto sobre botones
    onSecondary = Color.Black,       // Texto sobre acentos verdes
    onTertiary = WhiteText,          // Texto sobre púrpura
    onBackground = WhiteText,        // Texto principal
    onSurface = LightGrey            // Texto secundario
)

@Composable
fun LevelUPGamerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
