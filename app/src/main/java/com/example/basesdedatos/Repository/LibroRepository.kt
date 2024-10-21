package com.example.basesdedatos.Repository


import com.example.basesdedatos.DAO.AutoresDao
import com.example.basesdedatos.DAO.LibroDao
import com.example.basesdedatos.Model.Autores
import com.example.basesdedatos.Model.Libro

class LibroRepository(private val libroDao: LibroDao){

    suspend fun insertLibro(libro: Libro) {
        libroDao.insertLibro(libro)
    }
    suspend fun getLibroById(id: Int, titulo: String, genero: String, autor_id: Int)  {
        libroDao.getLibroById(id, titulo, genero, autor_id)
    }
    suspend fun getAllLibros(): List<Libro> {
        return libroDao.getAllLibros()
    }
    suspend fun deleteLibroById(id: Int) : Int {
        return libroDao.deleteLibroById(id)
    }
    suspend fun getLibrosByAutor(autor_id: Int): List<Libro>{
        return libroDao.getLibrosByAutor(autor_id)
    }
}