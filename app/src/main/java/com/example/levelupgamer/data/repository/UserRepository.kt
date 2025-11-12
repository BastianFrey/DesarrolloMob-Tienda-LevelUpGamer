package com.example.levelupgamer.data.repository

import com.example.levelupgamer.data.model.User
import com.example.levelupgamer.data.dao.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun register(user: User) {
        userDao.insert(user)
    }

    suspend fun login(nombre: String, contrasena: String): User? {
        return userDao.login(nombre, contrasena)
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }

    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    suspend fun getUserByNombre(nombreReferido: String): User? {
        return userDao.getUserByNombre(nombreReferido)
    }
}