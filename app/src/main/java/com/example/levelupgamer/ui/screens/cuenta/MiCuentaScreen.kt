package com.example.levelupgamer.ui.screens.cuenta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelupgamer.viewmodel.CarritoViewModel
import com.example.levelupgamer.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiCuentaScreen(navController: NavController, userViewModel: UserViewModel) {
    val colorScheme = MaterialTheme.colorScheme

    val currentUser by userViewModel.currentUser.collectAsState()
    val carritoViewModel: CarritoViewModel = viewModel()

    var isEditing by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf<String?>(null) }

    var editedNombre by remember { mutableStateOf("") }
    var editedCorreo by remember { mutableStateOf("") }
    var editedContrasena by remember { mutableStateOf("") }
    var editedAnioNacimiento by remember { mutableStateOf("") }

    LaunchedEffect(currentUser) {
        currentUser?.let {
            editedNombre = it.nombre
            editedCorreo = it.correo
            editedAnioNacimiento = it.anioNacimiento.toString()
        }
    }

    Scaffold(
        modifier = Modifier.background(colorScheme.background),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mi Cuenta",
                        color = colorScheme.secondary,
                        fontFamily = FontFamily.Default
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
                actions = {
                    if (currentUser != null && !isEditing) {
                        IconButton(onClick = { isEditing = true }) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Editar",
                                tint = colorScheme.primary
                            )
                        }
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
        ) {
            if (currentUser == null) {
                // Estado: No logueado
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "No logueado",
                        tint = colorScheme.secondary,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "No has iniciado sesión",
                        style = MaterialTheme.typography.titleMedium,
                        color = colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Inicia sesión para ver tu cuenta",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            navController.navigate("welcome") {
                                popUpTo("miCuenta") { inclusive = true }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorScheme.secondary,
                            contentColor = colorScheme.onSecondary
                        )
                    ) {
                        Text("Iniciar Sesión")
                    }
                }
            } else {
                val user = currentUser!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Usuario",
                                tint = colorScheme.secondary,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Bienvenido, ${user.nombre}",
                                style = MaterialTheme.typography.titleMedium,
                                color = colorScheme.secondary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Level-Up Gamer",
                                style = MaterialTheme.typography.bodyMedium,
                                color = colorScheme.primary
                            )
                        }
                    }

                    mensaje?.let {
                        Text(
                            text = it,
                            color = if (it.contains("éxito") || it.contains("correctamente")) colorScheme.secondary else colorScheme.error,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    if (isEditing) {
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
                                    "Editar Información",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = colorScheme.secondary,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )

                                OutlinedTextField(
                                    value = editedNombre,
                                    onValueChange = { editedNombre = it },
                                    label = { Text("Nombre", color = colorScheme.secondary) },
                                    textStyle = LocalTextStyle.current.copy(color = colorScheme.onSurface),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                OutlinedTextField(
                                    value = editedCorreo,
                                    onValueChange = { editedCorreo = it },
                                    label = { Text("Correo", color = colorScheme.secondary) },
                                    textStyle = LocalTextStyle.current.copy(color = colorScheme.onSurface),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                OutlinedTextField(
                                    value = editedContrasena,
                                    onValueChange = { editedContrasena = it },
                                    label = { Text("Nueva Contraseña (opcional)", color = colorScheme.secondary) },
                                    textStyle = LocalTextStyle.current.copy(color = colorScheme.onSurface),
                                    visualTransformation = PasswordVisualTransformation(),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                OutlinedTextField(
                                    value = editedAnioNacimiento,
                                    onValueChange = { editedAnioNacimiento = it },
                                    label = { Text("Año de Nacimiento", color = colorScheme.secondary) },
                                    textStyle = LocalTextStyle.current.copy(color = colorScheme.onSurface),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                // Botones de acción
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Button(
                                        onClick = {
                                            isEditing = false
                                            editedNombre = user.nombre
                                            editedCorreo = user.correo
                                            editedContrasena = ""
                                            editedAnioNacimiento = user.anioNacimiento.toString()
                                            mensaje = null
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = colorScheme.tertiary,
                                            contentColor = colorScheme.onTertiary
                                        )
                                    ) {
                                        Text("Cancelar")
                                    }

                                    Button(
                                        onClick = {
                                            val correoValido = editedCorreo.endsWith("@duocuc.cl") || editedCorreo.endsWith("@gmail.com") || editedCorreo.endsWith("@admin.cl")
                                            val anioValido = editedAnioNacimiento.toIntOrNull()?.let { it <= 2005 } ?: false
                                            when {
                                                editedNombre.isBlank() || editedCorreo.isBlank() || editedAnioNacimiento.isBlank() -> {
                                                    mensaje = "Todos los campos son obligatorios"
                                                }
                                                !correoValido -> {
                                                    mensaje = "El correo debe terminar en @duocuc.cl, @gmail.com o @admin.cl"
                                                }
                                                !anioValido -> {
                                                    mensaje = "Debes ser mayor de 18 años"
                                                }
                                                else -> {
                                                    val updatedUser = user.copy(
                                                        nombre = editedNombre,
                                                        correo = editedCorreo,
                                                        contrasena = if (editedContrasena.isNotBlank()) editedContrasena else user.contrasena,
                                                        anioNacimiento = editedAnioNacimiento.toInt()
                                                    )
                                                    userViewModel.updateUser(updatedUser) { success, errorMessage ->
                                                        if (success) {
                                                            mensaje = "Datos actualizados correctamente"
                                                            isEditing = false
                                                            editedContrasena = ""
                                                        } else {
                                                            mensaje = errorMessage ?: "Error al actualizar los datos"
                                                        }
                                                    }
                                                }
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = colorScheme.secondary,
                                            contentColor = colorScheme.onSecondary
                                        )
                                    ) {
                                        Text("Guardar Cambios")
                                    }
                                }
                            }
                        }
                    } else {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = colorScheme.surface
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                InfoRow(etiqueta = "Nombre:", valor = user.nombre, color = colorScheme.secondary)
                                InfoRow(etiqueta = "Correo:", valor = user.correo, color = colorScheme.primary)
                                InfoRow(etiqueta = "Año de Nacimiento:", valor = user.anioNacimiento.toString(), color = colorScheme.secondary)
                                InfoRow(etiqueta = "Edad:", valor = calcularEdad(user.anioNacimiento).toString() + " años", color = colorScheme.primary)

                                Spacer(modifier = Modifier.height(16.dp))

                                if (user.correo.endsWith("@duocuc.cl")) {
                                    Text(
                                        "🎓 Beneficio DuocUC: 20% de descuento permanente",
                                        color = colorScheme.secondary,
                                        fontWeight = FontWeight.Bold
                                    )
                                } else {
                                    Text(
                                        "👤 Cuenta estándar",
                                        color = colorScheme.primary,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = {
                                userViewModel.logout()
                                carritoViewModel.limpiarCarrito()
                                navController.popBackStack()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorScheme.error,
                                contentColor = colorScheme.onError
                            )
                        ) {
                            Text("Cerrar Sesión")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(etiqueta: String, valor: String, color: Color) {
    val colorScheme = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = etiqueta,
            color = colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = valor,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

fun calcularEdad(anioNacimiento: Int): Int {
    return java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) - anioNacimiento
}
