package com.example.basesdedatos.Model

import androidx.room.Embedded
import androidx.room.Relation

data class PrestamoLibroMiembro(
    @Embedded val prestamo: Prestamos,
    @Relation(
        parentColumn = "libro_id",
        entityColumn = "libro_id"
    )
    val libro: Libro,
    @Relation(
        parentColumn = "miembro_id",
        entityColumn = "miembro_id"
    )
    val miembro: Miembros
)
