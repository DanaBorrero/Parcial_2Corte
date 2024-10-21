package com.example.basesdedatos.Model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MiembroConLibros(
    @Embedded val miembro: Miembros,
    @Relation(
        parentColumn = "miembro_id",
        entityColumn = "libro_id",
        associateBy = Junction(Prestamos::class)
    )
    val libros: List<Libro>
)
