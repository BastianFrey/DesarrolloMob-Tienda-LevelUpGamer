package com.example.levelupgamer.ui.screens.producto

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelupgamer.R
import com.example.levelupgamer.data.model.Producto
import com.example.levelupgamer.viewmodel.CarritoViewModel
import com.example.levelupgamer.viewmodel.ProductoViewModel
import com.example.levelupgamer.viewmodel.UserViewModel

private val categoriasCaso = listOf(
    "Todos",
    "Juegos de Mesa",
    "Accesorios",
    "Consolas",
    "Computadores Gamers",
    "Sillas Gamers",
    "Mouse",
    "Mousepad",
    "Poleras gamers"
)

@Composable
fun ProductoListScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    productoViewModel: ProductoViewModel = viewModel(),
    carritoViewModel: CarritoViewModel = viewModel()
) {
    val productos by productoViewModel.productos.collectAsState()
    val currentUser by userViewModel.currentUser.collectAsState()
    val isAdmin = currentUser?.correo?.endsWith("@admin.cl") == true

    // filtro seleccionado
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }

    // aplicar filtro a la lista
    val productosFiltrados = remember(productos, categoriaSeleccionada) {
        if (categoriaSeleccionada == "Todos") productos
        else productos.filter { it.categoria == categoriaSeleccionada }
    }

    Scaffold(
        containerColor = Color(0xFF121212),
        floatingActionButton = {
            if (isAdmin) {
                FloatingActionButton(
                    onClick = { navController.navigate("agregarProducto") },
                    containerColor = Color(0xFF39FF14),
                    contentColor = Color.Black
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Agregar Producto")
                }
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            // --------- FILTROS ARRIBA ---------
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categoriasCaso.size) { index ->
                    val cat = categoriasCaso[index]
                    val seleccionado = cat == categoriaSeleccionada
                    FilterChip(
                        selected = seleccionado,
                        onClick = { categoriaSeleccionada = cat },
                        label = {
                            Text(
                                text = cat,
                                color = if (seleccionado) Color.Black else Color.White,
                                fontSize = 12.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF39FF14),
                            containerColor = Color(0xFF2E2E2E)
                        )
                    )
                }
            }

            if (productosFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay productos para esta categoría",
                        color = Color.White
                    )
                }
            } else {
                // --------- GRID COMO TENÍAS ---------
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(productosFiltrados) { prod ->
                        ProductoCard(
                            producto = prod,
                            navController = navController,
                            isAdmin = isAdmin,
                            onEdit = { navController.navigate("editProducto/${prod.id}") },
                            onDelete = { productoViewModel.eliminarProducto(prod) },
                            onAddToCart = { carritoViewModel.agregarAlCarrito(prod) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductoCard(
    producto: Producto,
    navController: NavController,
    isAdmin: Boolean,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onAddToCart: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clickable { navController.navigate("productoDetalle/${producto.id}") },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2E2E2E)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = productoImage(producto.nombre)),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF555555))
                    .padding(8.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = producto.nombre,
                        color = Color(0xFF39FF14),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 2
                    )
                    Text(
                        text = "$${producto.precio}",
                        color = Color(0xFF39FF14),
                        fontSize = 12.sp
                    )
                }

                if (isAdmin) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = onEdit,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Editar", color = Color.Black, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Button(
                            onClick = onDelete,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Eliminar", color = Color.White, fontSize = 12.sp)
                        }
                    }
                } else {
                    Button(
                        onClick = onAddToCart,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B5BFF)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Agregar al carrito", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

private fun productoImage(nombre: String): Int {
    return when {
        nombre.contains("catan", ignoreCase = true) -> R.drawable.catan
        nombre.contains("carcassonne", ignoreCase = true) -> R.drawable.carcassone
        nombre.contains("xbox", ignoreCase = true) -> R.drawable.mandoxbox
        nombre.contains("hyperx", ignoreCase = true) -> R.drawable.audifonos
        nombre.contains("playstation", ignoreCase = true) || nombre.contains("ps5", ignoreCase = true) -> R.drawable.ps5
        nombre.contains("pc", ignoreCase = true) && nombre.contains("gamer", ignoreCase = true) -> R.drawable.pc
        nombre.contains("silla", ignoreCase = true) -> R.drawable.silla
        nombre.contains("mousepad", ignoreCase = true) -> R.drawable.mousepad
        nombre.contains("mouse", ignoreCase = true) -> R.drawable.mouse
        nombre.contains("polera", ignoreCase = true) -> R.drawable.polera
        else -> R.drawable.ic_launcher_background
    }
}