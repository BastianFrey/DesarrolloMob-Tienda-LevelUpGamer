package com.example.levelupgamer.data.network

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object TokenManager {
    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

    fun setToken(token: String) {
        _token.value = token
    }

    fun clearToken() {
        _token.value = null
    }
}