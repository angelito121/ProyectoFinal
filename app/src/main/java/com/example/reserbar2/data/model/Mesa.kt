package com.example.reserbar2.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mesa")
data class Mesa(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val capacidad: String
)