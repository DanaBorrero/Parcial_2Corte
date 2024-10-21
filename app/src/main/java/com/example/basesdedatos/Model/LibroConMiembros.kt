package com.example.basesdedatos.Model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class LibroConMiembros(
    @Embedded val libro: Libro,
    @Relation(
        parentColumn = "libro_id",
        entityColumn = "miembro_id",
        associateBy = Junction(Prestamos::class)
    )
    val miembros: List<Miembros>
)
