package com.example.levelupgamer.data.dao

import androidx.room.*
import com.example.levelupgamer.data.model.User
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM users WHERE nombre = :nombre AND contrasena = :contrasena")
    suspend fun login(nombre: String, contrasena: String): User?

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT * FROM users WHERE nombre = :nombreReferido")
    suspend fun getUserByNombre(nombreReferido: String): User?

}