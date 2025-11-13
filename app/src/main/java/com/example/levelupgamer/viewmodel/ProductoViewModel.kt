package com.example.levelupgamer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.levelupgamer.data.database.ProductoDatabase
import com.example.levelupgamer.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductoViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        ProductoDatabase::class.java,
        "levelupgamer.db"
    ).build()

    private val _todosLosProductos = db.productoDao().getAllProductos()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Todos")
    val selectedCategory = _selectedCategory.asStateFlow()

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }

    val productosFiltrados: StateFlow<List<Producto>> =
        combine(_todosLosProductos, _searchText, _selectedCategory) { productos, text, category ->

            val productosPorCategoria = if (category == "Todos") {
                productos
            } else {
                productos.filter { it.categoria.equals(category, ignoreCase = true) }
            }

            if (text.isBlank()) {
                productosPorCategoria // Si no hay texto, devolvemos la lista (ya filtrada por categoría)
            } else {
                productosPorCategoria.filter { producto ->
                    producto.nombre.contains(text, ignoreCase = true) ||
                            producto.descripcion.contains(text, ignoreCase = true)
                }
            }
        }
            .stateIn(
                viewModelScope,
                kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    init {
        viewModelScope.launch {
            val dao = db.productoDao()
            val actuales = dao.getAllProductos().first()
            val nombresActuales = actuales.map { it.nombre }.toSet()

            val examen = listOf(
                Producto(
                    nombre = "Catan",
                    descripcion = "JM001 • Juegos de Mesa • Catan",
                    precio = 29990.0,
                    categoria = "Juegos de Mesa"
                ),
                Producto(
                    nombre = "Carcassonne",
                    descripcion = "JM002 • Juegos de Mesa • Carcassonne",
                    precio = 24990.0,
                    categoria = "Juegos de Mesa"
                ),
                Producto(
                    nombre = "Controlador Inalámbrico Xbox Series X",
                    descripcion = "AC001 • Accesorios",
                    precio = 59990.0,
                    categoria = "Accesorios"
                ),
                Producto(
                    nombre = "Auriculares Gamer HyperX Cloud II",
                    descripcion = "AC002 • Accesorios",
                    precio = 79990.0,
                    categoria = "Accesorios"
                ),
                Producto(
                    nombre = "PlayStation 5",
                    descripcion = "CO001 • Consolas",
                    precio = 549990.0,
                    categoria = "Consolas"
                ),
                Producto(
                    nombre = "PC Gamer ASUS ROG Strix",
                    descripcion = "CG001 • Computadores Gamers",
                    precio = 1299990.0,
                    categoria = "Computadores Gamers"
                ),
                Producto(
                    nombre = "Silla Gamer Secretlab Titan",
                    descripcion = "SG001 • Sillas Gamers",
                    precio = 349990.0,
                    categoria = "Sillas Gamers"
                ),
                Producto(
                    nombre = "Mouse Gamer Logitech G502 HERO",
                    descripcion = "MS001 • Mouse",
                    precio = 49990.0,
                    categoria = "Mouse"
                ),
                Producto(
                    nombre = "Mousepad Razer Goliathus Extended",
                    descripcion = "MP001 • Mousepad",
                    precio = 29990.0,
                    categoria = "Mousepad"
                ),
                Producto(
                    nombre = "Polera Gamer Personalizada \"Level-Up\"",
                    descripcion = "PP001 • Poleras gamers",
                    precio = 14990.0,
                    categoria = "Poleras gamers"
                )
            )

            examen.forEach { prod ->
                if (prod.nombre !in nombresActuales) {
                    dao.insert(prod)
                }
            }
        }
    }

    fun getProducto(id: Int) = db.productoDao().getProductoById(id)

    fun agregarProducto(nombre: String, descripcion: String, precio: Double, categoria: String) {
        viewModelScope.launch {
            db.productoDao().insert(
                Producto(
                    nombre = nombre,
                    descripcion = descripcion,
                    precio = precio,
                    categoria = categoria
                )
            )
        }
    }

    fun actualizarProducto(producto: Producto) {
        viewModelScope.launch {
            db.productoDao().update(producto)
        }
    }

    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch {
            db.productoDao().delete(producto)
        }
    }
}