package com.example.levelupgamer.data.network

import com.example.levelupgamer.data.model.Producto
import com.example.levelupgamer.data.model.auth.LoginRequest
import com.example.levelupgamer.data.model.auth.LoginResponse
import com.example.levelupgamer.data.model.reviews.ResenaStatsDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("products")
    suspend fun getAllProductos(): Response<List<Producto>>

    @GET("products/{id}/reviews")
    suspend fun getResenasPorProducto(@Path("id") productoId: Long): Response<ResenaStatsDTO>

    // --- MÁS ADELANTE AÑADIREMOS ---
    // @POST("users/register")
    // @POST("products/{id}/reviews")
    // @POST("orders")
}