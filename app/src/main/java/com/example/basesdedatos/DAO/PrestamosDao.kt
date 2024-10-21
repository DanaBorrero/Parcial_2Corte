package com.example.basesdedatos.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.basesdedatos.Model.Autores
import com.example.basesdedatos.Model.PrestamoLibroMiembro

@Dao
interface PrestamosDao {
    @Transaction
    @Query("SELECT * FROM prestamos WHERE prestamo_id = :prestamoId")
    suspend fun getPrestamoLibroMiembro(prestamoId: Int): PrestamoLibroMiembro

    @Transaction
    @Query("SELECT * FROM prestamos")
    suspend fun getAllPrestamosLibroMiembro(): List<PrestamoLibroMiembro>
}
