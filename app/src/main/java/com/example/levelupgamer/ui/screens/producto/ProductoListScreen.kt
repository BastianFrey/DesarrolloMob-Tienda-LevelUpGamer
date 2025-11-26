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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val colorScheme = MaterialTheme.colorScheme

    val currentUser by userViewModel.currentUser.collectAsState()
    val isAdmin = currentUser?.correo?.endsWith("@admin.cl") == true

    val searchText by productoViewModel.searchText.collectAsState()
    val categoriaSeleccionada by productoViewModel.selectedCategory.collectAsState()

    val productosFiltrados by productoViewModel.productosFiltrados.collectAsState()

    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var productToDelete by remember { mutableStateOf<Producto?>(null) }

    Scaffold(
        containerColor = colorScheme.background,
        floatingActionButton = {
            if (isAdmin) {
                FloatingActionButton(
                    onClick = { navController.navigate("agregarProducto") },
                    containerColor = colorScheme.secondary,
                    contentColor = colorScheme.onSecondary
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
                .background(colorScheme.background)
        ) {

            OutlinedTextField(
                value = searchText,
                onValueChange = productoViewModel::onSearchTextChange,
                label = { Text("Buscar producto...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                    focusedIndicatorColor = colorScheme.primary,
                    unfocusedIndicatorColor = colorScheme.surface
                )
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 10.dp)
            ) {
                items(categoriasCaso.size) { index ->
                    val cat = categoriasCaso[index]
                    val seleccionado = (cat == categoriaSeleccionada)
                    FilterChip(
                        selected = seleccionado,
                        onClick = { productoViewModel.onCategorySelected(cat) },
                        label = {
                            Text(
                                text = cat,
                                color = if (seleccionado) colorScheme.onSecondary else colorScheme.onBackground,
                                fontSize = 12.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = colorScheme.secondary,
                            containerColor = colorScheme.surface
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
                        text = "No se encontraron productos",
                        color = colorScheme.onBackground
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(productosFiltrados, key = { it.id }) { prod ->
                        ProductoCard(
                            producto = prod,
                            navController = navController,
                            isAdmin = isAdmin,
                            onEdit = { navController.navigate("editProducto/${prod.id}") },
                            onDelete = {
                                productToDelete = prod
                                showDeleteConfirmation = true
                            },
                            onAddToCart = { carritoViewModel.agregarAlCarrito(prod) }
                        )
                    }
                }
            }
        }
    }

    // ⬇️ DIÁLOGO DE CONFIRMACIÓN DE ELIMINACIÓN
    if (showDeleteConfirmation && productToDelete != null) {
        val producto = productToDelete!!
        AlertDialog(
            onDismissRequest = {
                showDeleteConfirmation = false
                productToDelete = null
            },
            title = { Text("Confirmar Eliminación", color = colorScheme.error) },
            text = { Text("¿Estás seguro de que deseas eliminar permanentemente el producto: \"${producto.nombre}\"? Esta acción no se puede deshacer.") },
            confirmButton = {
                Button(
                    onClick = {
                        productoViewModel.eliminarProducto(producto)
                        showDeleteConfirmation = false
                        productToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorScheme.error)
                ) {
                    Text("Eliminar", color = colorScheme.onError)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteConfirmation = false
                        productToDelete = null
                    }
                ) {
                    Text("Cancelar", color = colorScheme.secondary)
                }
            },
            containerColor = colorScheme.surface
        )
    }
    // ⬆️ FIN DIÁLOGO DE CONFIRMACIÓN
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
    val colorScheme = MaterialTheme.colorScheme

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clickable { navController.navigate("productoDetalle/${producto.id}") },
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface
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
                    .background(colorScheme.surface)
                    .padding(8.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = producto.nombre,
                        color = colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 2
                    )
                    Text(
                        text = "$${producto.precio}",
                        color = colorScheme.secondary,
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
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorScheme.primary
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Editar", color = colorScheme.onPrimary, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Button(
                            onClick = onDelete,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorScheme.error
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Eliminar", color = colorScheme.onError, fontSize = 12.sp)
                        }
                    }
                } else {
                    Button(
                        onClick = onAddToCart,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorScheme.primary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Agregar al carrito", color = colorScheme.onPrimary, fontSize = 12.sp)
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