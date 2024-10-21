package com.example.basesdedatos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.basesdedatos.DAO.AutoresDao
import com.example.basesdedatos.DAO.LibroDao
import com.example.basesdedatos.Repository.AutorRepository
import com.example.basesdedatos.Database.AppDatabase
import com.example.basesdedatos.Repository.LibroRepository
import com.example.basesdedatos.Screen.Navigation
import com.example.basesdedatos.ui.theme.BasesDeDatosTheme

class MainActivity : ComponentActivity() {
    private lateinit var autoresDao: AutoresDao
    private lateinit var libroDao: LibroDao
    private lateinit var autorRepository: AutorRepository
    private lateinit var libroRepository: LibroRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(applicationContext)
        autoresDao = db.autorDao()
        libroDao = db.libroDao()
        autorRepository = AutorRepository(autoresDao)
        libroRepository = LibroRepository(libroDao)

        enableEdgeToEdge()
        setContent {
            BasesDeDatosTheme {
                Navigation(autorRepository, libroRepository)
            }

        }
    }
}

