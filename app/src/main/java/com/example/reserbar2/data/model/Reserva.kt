package com.example.reserbar2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservas")
data class Reserva(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mesa: Int,
    val fecha: String,
    val hora: String
)