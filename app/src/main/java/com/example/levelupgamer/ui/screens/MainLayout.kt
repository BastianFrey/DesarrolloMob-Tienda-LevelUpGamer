package com.example.levelupgamer.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    navController: NavController,
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(220.dp),
                drawerContainerColor = colorScheme.primary
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Menú",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = colorScheme.secondary,
                        fontFamily = FontFamily.Default
                    ),
                    modifier = Modifier.padding(16.dp)
                )

                HorizontalDivider(color = colorScheme.surface, thickness = 1.dp)

                // Menú
                NavigationDrawerItem(
                    label = { Text("Inicio", color = colorScheme.secondary, fontFamily = FontFamily.Default) },
                    selected = false,
                    onClick = {
                        navController.navigate("home")
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text("Productos", color = colorScheme.secondary, fontFamily = FontFamily.Default) },
                    selected = false,
                    onClick = {
                        navController.navigate("productos")
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text("Mi Cuenta", color = colorScheme.secondary, fontFamily = FontFamily.Default) },
                    selected = false,
                    onClick = {
                        navController.navigate("miCuenta")
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text("Escanear QR", color = colorScheme.secondary, fontFamily = FontFamily.Default) },
                    selected = false,
                    onClick = {
                        navController.navigate("scanner")
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text("Sobre Nosotros", color = colorScheme.secondary, fontFamily = FontFamily.Default) },
                    selected = false,
                    onClick = {
                        navController.navigate("nosotros")
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text("Contacto", color = colorScheme.secondary, fontFamily = FontFamily.Default) },
                    selected = false,
                    onClick = {
                        navController.navigate("contacto")
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(title, color = colorScheme.secondary) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú", tint = colorScheme.secondary)
                        }
                    },
                    actions = actions,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorScheme.background,
                        titleContentColor = colorScheme.secondary
                    )
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                content()
            }
        }
    }
}