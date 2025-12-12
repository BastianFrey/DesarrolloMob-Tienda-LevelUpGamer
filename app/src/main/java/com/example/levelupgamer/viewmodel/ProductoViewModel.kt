package com.example.levelupgamer.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.levelupgamer.data.database.ProductoDatabase
import com.example.levelupgamer.data.model.Producto
import com.example.levelupgamer.data.network.RetrofitClient // Asegúrate de tener este import
import com.example.levelupgamer.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductoViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        ProductoDatabase::class.java,
        "levelupgamer.db"
    )
        .fallbackToDestructiveMigration()
        .build()

    private val repository = ProductoRepository(db.productoDao())

    private val _todosLosProductos = MutableStateFlow<List<Producto>>(emptyList())

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Todos")
    val selectedCategory = _selectedCategory.asStateFlow()

    val productosFiltrados: StateFlow<List<Producto>> =
        combine(_todosLosProductos, _searchText, _selectedCategory) { productos, text, category ->

            val productosPorCategoria = if (category == "Todos") {
                productos
            } else {
                productos.filter { it.categoria.equals(category, ignoreCase = true) }
            }

            if (text.isBlank()) {
                productosPorCategoria
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
        cargarProductosDesdeBackend()
    }

    fun cargarProductosDesdeBackend() {
        viewModelScope.launch {
            try {
                val listaRemota = repository.obtenerProductosDeApi()

                if (listaRemota.isNotEmpty()) {
                    Log.d("API_SUCCESS", "Se cargaron ${listaRemota.size} productos")
                    _todosLosProductos.value = listaRemota
                } else {
                    Log.e("API_WARN", "El backend devolvió una lista vacía o falló la conexión")
                }
            } catch (e: Exception) {
                Log.e("API_FAIL", "Error crítico al cargar productos: ${e.message}")
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }

    fun getProducto(id: Long): Producto? {
        return _todosLosProductos.value.find { it.id.toLong() == id }
    }

    fun agregarProducto(
        token: String,
        nombre: String,
        descripcion: String,
        precio: Double,
        categoria: String,
        imagenUrl: String
    ) {
        viewModelScope.launch {
            val nuevoProducto = Producto(
                nombre = nombre,
                descripcion = descripcion,
                precio = precio,
                categoria = categoria,
                imagenUrl = imagenUrl,
                activo = true
            )

            try {
                val response = RetrofitClient.instance.crearProducto(token, nuevoProducto)

                if (response.isSuccessful) {
                    Log.d("API_ADD", "Producto creado exitosamente: ${response.body()?.nombre}")
                    cargarProductosDesdeBackend()
                } else {
                    Log.e("API_ADD", "Error al crear producto: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("API_ADD", "Fallo de conexión al crear: ${e.message}")
            }
        }
    }

    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch {
            db.productoDao().delete(producto)
            val listaActual = _todosLosProductos.value.toMutableList()
            listaActual.remove(producto)
            _todosLosProductos.value = listaActual
        }
    }

    fun actualizarProducto(producto: Producto) {
        viewModelScope.launch {
            val exito = repository.actualizarProductoEnApi(producto)

            if (exito) {
                cargarProductosDesdeBackend()
            } else {
                Log.e("VIEWMODEL", "No se pudo actualizar el producto")
            }
        }
    }
}