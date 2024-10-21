package com.example.basesdedatos.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.basesdedatos.Model.Libro
import com.example.basesdedatos.Model.LibroConMiembros

@Dao
interface LibroDao {
    @Insert
    suspend fun insertLibro(libro: Libro)

    @Query("SELECT * FROM libros")
    suspend fun getAllLibros(): List<Libro>

    @Query("DELETE FROM libros WHERE libro_id = :id")
    suspend fun deleteLibroById(id: Int): Int

    @Query("UPDATE libros SET titulo = :titulo, genero = :genero, autor_id = :autor_id WHERE libro_id = :id")
    suspend fun getLibroById(id: Int, titulo: String, genero: String, autor_id: Int): Int

    @Transaction
    @Query("SELECT * FROM libros WHERE autor_id = :libroId")
    suspend fun getLibroConMiembros(libroId: Int): LibroConMiembros

    // Obtener todos los libros de un autor específico usando el autor_id (clave foránea)
    @Query("SELECT * FROM libros WHERE autor_id = :autorId")
    suspend fun getLibrosByAutor(autorId: Int): List<Libro>

}