package com.example.levelupgamer.data.network

import com.example.levelupgamer.data.model.LoginRequest
import com.example.levelupgamer.data.model.LoginResponse
import com.example.levelupgamer.data.model.Producto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("api/products")
    suspend fun obtenerProductos(): Response<List<Producto>>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @PUT("api/products/{id}")
    suspend fun actualizarProducto(@Path("id") id: Int, @Body producto: Producto): Response<Producto>
}