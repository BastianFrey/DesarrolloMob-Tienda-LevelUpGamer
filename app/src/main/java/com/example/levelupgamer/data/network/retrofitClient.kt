package com.example.levelupgamer.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Si usas emulador: "http://10.0.2.2:8080/"
    // Si usas celular físico: "http://TU_IP_LOCAL:8080/" (Ej: "http://192.168.1.15:8080/")
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}