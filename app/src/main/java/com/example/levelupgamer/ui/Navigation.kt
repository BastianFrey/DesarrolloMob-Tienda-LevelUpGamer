package com.example.levelupgamer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.levelupgamer.ui.screens.EditProductoScreen
import com.example.levelupgamer.ui.screens.MainLayout
import com.example.levelupgamer.ui.screens.ScannerScreen
import com.example.levelupgamer.ui.screens.evento.EventosScreen
import com.example.levelupgamer.ui.screens.noticia.NoticiasScreen
import com.example.levelupgamer.ui.screens.carrito.CarritoScreen
import com.example.levelupgamer.ui.screens.cuenta.MiCuentaScreen
import com.example.levelupgamer.ui.screens.home.HomeScreen
import com.example.levelupgamer.ui.screens.login.LoginScreen
import com.example.levelupgamer.ui.screens.producto.AgregarProductoScreen
import com.example.levelupgamer.ui.screens.producto.ProductoDetailScreen
import com.example.levelupgamer.ui.screens.producto.ProductoListScreen
import com.example.levelupgamer.ui.screens.register.RegisterScreen
import com.example.levelupgamer.ui.screens.welcome.ContactoScreen
import com.example.levelupgamer.ui.screens.welcome.NosotrosScreen
import com.example.levelupgamer.ui.screens.welcome.WelcomeScreen
import com.example.levelupgamer.viewmodel.CarritoViewModel
import com.example.levelupgamer.viewmodel.ProductoViewModel
import com.example.levelupgamer.viewmodel.UserViewModel
import com.example.levelupgamer.viewmodel.EventoViewModel
import com.example.levelupgamer.viewmodel.NoticiaViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()
    val carritoViewModel: CarritoViewModel = viewModel()
    val productoViewModel: ProductoViewModel = viewModel()
    val eventoViewModel: EventoViewModel = viewModel()
    val noticiaViewModel: NoticiaViewModel = viewModel()

    val productosCarrito by carritoViewModel.productosCarrito.collectAsState()
    val cantidadCarrito = productosCarrito.sumOf { it.cantidad }

    NavHost(navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("login") { LoginScreen(navController, userViewModel) }
        composable("register") { RegisterScreen(navController, userViewModel) }

        composable(
            "home",
            deepLinks = listOf(navDeepLink { uriPattern = "levelupgamer://inicio" })
        ) {
            MainLayout(navController = navController, title = "Bienvenido") {
                HomeScreen(
                    navController = navController,
                    productoViewModel = productoViewModel,
                    eventoViewModel = eventoViewModel,
                    noticiaViewModel = noticiaViewModel
                )
            }
        }

        composable("eventos") {
            MainLayout(navController = navController, title = "Eventos LevelUp") {
                EventosScreen(navController, eventoViewModel)
            }
        }
        composable("noticias") {
            MainLayout(navController = navController, title = "Noticias Gaming") {
                NoticiasScreen(navController, noticiaViewModel)
            }
        }

        composable("productos") {
            MainLayout(
                navController = navController,
                title = "Lista de Productos",
                actions = {
                    val colorScheme = MaterialTheme.colorScheme
                    IconButton(onClick = { navController.navigate("carrito") }) {
                        Box {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = "Carrito",
                                tint = colorScheme.secondary
                            )
                            if (cantidadCarrito > 0) {
                                Badge(
                                    modifier = Modifier.align(Alignment.TopEnd),
                                ) {
                                    Text(
                                        cantidadCarrito.toString(),
                                        color = colorScheme.onBackground
                                    )
                                }
                            }
                        }
                    }
                }
            ) {
                ProductoListScreen(navController, userViewModel, productoViewModel, carritoViewModel)
            }
        }

        composable("agregarProducto") {
            MainLayout(navController = navController, title = "Agregar Producto") {
                AgregarProductoScreen(navController, productoViewModel, userViewModel)
            }
        }

        composable("nosotros") { MainLayout(navController = navController, title = "Sobre Nosotros") { NosotrosScreen(navController) } }
        composable("contacto") { MainLayout(navController = navController, title = "Contacto") { ContactoScreen(navController) } }
        composable("miCuenta") { MainLayout(navController = navController, title = "Mi Cuenta") { MiCuentaScreen(navController, userViewModel) } }
        composable("carrito") { MainLayout(navController = navController, title = "Carrito") { CarritoScreen(navController, userViewModel) } }
        composable("scanner") { MainLayout(navController = navController, title = "Escanear QR") { ScannerScreen() } }

        composable(
            route = "productoDetalle/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            MainLayout(navController = navController, title = "Detalle del Producto") {
                ProductoDetailScreen(
                    navController = navController,
                    productoId = id,
                    userViewModel = userViewModel,
                    carritoViewModel = carritoViewModel
                )
            }
        }

        composable(
            route = "editProducto/{productoId}",
            arguments = listOf(navArgument("productoId") { type = NavType.LongType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getLong("productoId") ?: 0L
            MainLayout(navController = navController, title = "Editar Producto") {
                EditProductoScreen(navController, productoViewModel, productoId)
            }
        }
    }
}