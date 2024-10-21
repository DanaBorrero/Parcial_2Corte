package com.example.basesdedatos.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "prestamos",
    foreignKeys = [
        ForeignKey(
            entity = Libro::class,
            parentColumns = ["libro_id"],
            childColumns = ["libro_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Miembros::class,
            parentColumns = ["miembro_id"],
            childColumns = ["miembro_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Prestamos(
    @PrimaryKey(autoGenerate = true)
    val prestamo_id: Int,
    val libro_id: Int,
    val miembro_id: Int,
    val fecha_prestamo: String, // Usamos String para simplificar fechas
    val fecha_devolucion: String
)