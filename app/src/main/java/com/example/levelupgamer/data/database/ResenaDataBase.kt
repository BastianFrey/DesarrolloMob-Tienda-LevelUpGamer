package com.example.levelupgamer.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.levelupgamer.data.dao.ResenaDao
import com.example.levelupgamer.data.model.Resena

@Database(
    entities = [Resena::class],
    version = 1,
    exportSchema = false
)
abstract class ResenaDatabase : RoomDatabase() {
    abstract fun resenaDao(): ResenaDao

    companion object {
        @Volatile
        private var Instance: ResenaDatabase? = null

        fun getDatabase(context: Context): ResenaDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ResenaDatabase::class.java,
                    "resena_database"
                ).build().also { Instance = it }
            }
        }
    }
}