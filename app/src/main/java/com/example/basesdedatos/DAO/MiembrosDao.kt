package com.example.basesdedatos.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.basesdedatos.Model.MiembroConLibros
import com.example.basesdedatos.Model.Miembros

@Dao
interface MiembrosDao {
    @Insert
    suspend fun insertMiembro(miembro: Miembros)

    @Query("UPDATE miembros SET nombre = :nombre, apellido = :apellido, fecha_inscripcion = :fecha_inscripcion")
    suspend fun getMiembroById(nombre: String, apellido: String, fecha_inscripcion: String): Int

    @Query("SELECT * FROM miembros")
    suspend fun getAllMiembros(): List<Miembros>

    @Transaction
    @Query("SELECT * FROM miembros WHERE miembro_id = :miembroId")
    suspend fun getMiembroConLibros(miembroId: Int): MiembroConLibros
}