package com.example.basesdedatos.Model

import androidx.room.Embedded
import androidx.room.Relation

data class AutorConLibros(
    @Embedded val autores: Autores,
    @Relation(
        parentColumn = "id",
        entityColumn = "autor_id"
    )
    val libros: List<Libro>
)