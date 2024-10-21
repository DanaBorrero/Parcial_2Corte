package com.example.basesdedatos.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "libros",
    foreignKeys = [
        ForeignKey(
            entity = Autores::class,
            parentColumns = ["id"],
            childColumns = ["autor_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Libro(
    @PrimaryKey(autoGenerate = true)
    val libro_id: Int,
    val titulo: String,
    val genero: String,
    val autor_id: Int
)
