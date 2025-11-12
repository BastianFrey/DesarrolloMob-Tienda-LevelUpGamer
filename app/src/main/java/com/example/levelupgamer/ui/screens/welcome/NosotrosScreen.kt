package com.example.levelupgamer.ui.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NosotrosScreen(navController: NavController) {
    val colorScheme = MaterialTheme.colorScheme

    Scaffold(
        modifier = Modifier.background(colorScheme.background),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Sobre Nosotros",
                        color = colorScheme.secondary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = colorScheme.secondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "LEVEL-UP GAMER",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = colorScheme.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Tu Tienda Gaming en Chile",
                fontSize = 18.sp,
                color = colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Nuestra Misión
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "NUESTRA MISIÓN",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,

                        color = colorScheme.secondary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        "Proporcionar productos de alta calidad para gamers en todo Chile, " +
                                "ofreciendo una experiencia de compra única y personalizada, con un " +
                                "enfoque en la satisfacción del cliente y el crecimiento de la comunidad gamer.",
                        fontSize = 14.sp,
                        color = colorScheme.onBackground,
                        lineHeight = 20.sp
                    )
                }
            }


            // Nuestra Visión
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "NUESTRA VISIÓN",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        "Ser la tienda online líder en productos para gamers en Chile, " +
                                "reconocida por su innovación, servicio al cliente excepcional, " +
                                "y un programa de fidelización basado en gamificación que recompense " +
                                "a nuestros clientes más fieles.",
                        fontSize = 14.sp,
                        color = colorScheme.onBackground,
                        lineHeight = 20.sp
                    )
                }
            }


            // Nuestra Historia
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "NUESTRA HISTORIA",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.secondary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        "Level-Up Gamer nació hace dos años como respuesta a la creciente " +
                                "demanda durante la pandemia. Aunque no contamos con una ubicación " +
                                "física, realizamos despachos a todo el país, llevando la mejor " +
                                "experiencia gaming directamente a tu hogar.",
                        fontSize = 14.sp,
                        color = colorScheme.onBackground,
                        lineHeight = 20.sp
                    )
                }
            }


            // Nuestros Valores
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "NUESTROS VALORES",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.primary,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    ValorItem(
                        icon = "🎮",
                        titulo = "Pasion Gaming",
                        descripcion = "Vivimos y respiramos gaming, entendemos lo que necesitas."
                    )

                    ValorItem(
                        icon = "⚡",
                        titulo = "Innovación Constante",
                        descripcion = "Siempre a la vanguardia con los últimos productos y tecnologías."
                    )

                    ValorItem(
                        icon = "🤝",
                        titulo = "Comunidad First",
                        descripcion = "Nuestra comunidad gamer es el corazón de todo lo que hacemos."
                    )

                    ValorItem(
                        icon = "🚀",
                        titulo = "Calidad Garantizada",
                        descripcion = "Solo productos originales de primeras marcas."
                    )
                }
            }


            // Impacto Comunitario
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "IMPACTO COMUNITARIO",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.secondary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        "Tus compras apoyan directamente a la comunidad gamer chilena. " +
                                "Organizamos y patrocinamos eventos locales, torneos y meetups " +
                                "para fortalecer nuestra comunidad.",
                        fontSize = 14.sp,
                        color = colorScheme.onBackground,
                        lineHeight = 20.sp
                    )
                }
            }

            // Mensaje final
            Text(
                text = "\"PCFactory nos COPIO\"",
                fontSize = 16.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                color = colorScheme.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun ValorItem(icon: String, titulo: String, descripcion: String) {
    val colorScheme = MaterialTheme.colorScheme

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = icon,
            fontSize = 24.sp,
            color = colorScheme.onBackground,
            modifier = Modifier.padding(end = 12.dp)
        )
        Column {
            Text(
                text = titulo,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorScheme.secondary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = descripcion,
                fontSize = 14.sp,
                color = colorScheme.onBackground,
                lineHeight = 18.sp
            )
        }
    }
}