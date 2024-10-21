package com.example.basesdedatos.Database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.basesdedatos.DAO.AutoresDao
import com.example.basesdedatos.DAO.LibroDao
import com.example.basesdedatos.DAO.MiembrosDao
import com.example.basesdedatos.DAO.PrestamosDao
import com.example.basesdedatos.Model.Autores
import com.example.basesdedatos.Model.Libro
import com.example.basesdedatos.Model.Miembros
import com.example.basesdedatos.Model.Prestamos

@Database(entities = [Libro::class, Autores::class, Prestamos::class, Miembros::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun libroDao(): LibroDao
    abstract fun autorDao(): AutoresDao
    abstract fun prestamoDao(): PrestamosDao
    abstract fun miembroDao(): MiembrosDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}