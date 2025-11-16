package com.example.levelupgamer.data.repository

// Importaciones NUEVAS
import com.example.levelupgamer.data.model.auth.LoginRequest
import com.example.levelupgamer.data.model.auth.LoginResponse
import com.example.levelupgamer.data.network.ApiClient
import com.example.levelupgamer.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class AuthRepository {

    private val apiService: ApiService by lazy {
        ApiClient.retrofit.create(ApiService::class.java)
    }

    suspend fun login(correo: String, contrasena: String): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val request = LoginRequest(correo = correo, contrasena = contrasena)

                val response = apiService.login(request)

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Error desconocido del servidor"
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                Result.failure(Exception("No se pudo conectar al servidor: ${e.message}"))
            }
        }
    }
}