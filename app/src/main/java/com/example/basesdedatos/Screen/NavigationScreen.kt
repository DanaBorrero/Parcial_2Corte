package com.example.basesdedatos.Screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.basesdedatos.Repository.AutorRepository
import com.example.basesdedatos.Repository.LibroRepository
import com.example.basesdedatos.Screen.AutoresScreen.AutorScreen
import com.example.basesdedatos.Screen.LibroScreen.LibroScreen


@Composable
fun Navigation(autorRepository: AutorRepository, libroRepository: LibroRepository, ){
    val navController = rememberNavController()

    NavHost(navController = navController,
            startDestination = "Autor") {

        composable("Autor") {
            AutorScreen(autorRepository = autorRepository, navController = navController)
        }
        composable("Libro") {
            LibroScreen(libroRepository = libroRepository, navController = navController, autorRepository = autorRepository)
        }
    }
}

