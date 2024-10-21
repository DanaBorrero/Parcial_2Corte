package com.example.basesdedatos.DAO
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.basesdedatos.Model.AutorConLibros
import com.example.basesdedatos.Model.Autores

@Dao
interface AutoresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAutor(autor: Autores)

    @Query("SELECT * FROM autores")
    suspend fun getAllAutores(): List<Autores>

    @Query("UPDATE autores SET nombre = :nombre, apellido = :apellido, nacionalidad = :nacionalidad WHERE id = :id")
    suspend fun getAutorById(id: Int, nombre: String, apellido: String, nacionalidad: String): Int

    @Query("DELETE FROM autores WHERE id = :id")
    suspend fun deleteAutorById(id: Int): Int

    @Transaction
    @Query("SELECT * FROM autores WHERE id = :autorId")
    suspend fun getAutorConLibros(autorId: Int): List<AutorConLibros>

}


