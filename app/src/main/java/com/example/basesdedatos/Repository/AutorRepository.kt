package com.example.basesdedatos.Repository

import com.example.basesdedatos.DAO.AutoresDao
import com.example.basesdedatos.DAO.LibroDao
import com.example.basesdedatos.Model.AutorConLibros
import com.example.basesdedatos.Model.Autores
import com.example.basesdedatos.Model.Libro

class AutorRepository(private val autoresDao: AutoresDao){

    suspend fun insertAutor(autor: Autores) {
        autoresDao.insertAutor(autor)
    }
    suspend fun getAutorById(id: Int, nombre: String, apellido: String, nacionalidad: String) {
        autoresDao.getAutorById(id, nombre, apellido, nacionalidad)
    }
    suspend fun getAllAutores(): List<Autores> {
        return autoresDao.getAllAutores()
    }
    suspend fun deleteAutorById(id: Int) : Int {
        return autoresDao.deleteAutorById(id)
    }
    suspend fun getAutorConLibros(id: Int): List<AutorConLibros> {
        return autoresDao.getAutorConLibros(id)
    }
}