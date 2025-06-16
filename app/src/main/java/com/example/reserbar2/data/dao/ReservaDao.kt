package com.example.reserbar2.data.dao

import androidx.room.*
import com.example.reserbar2.data.model.Reserva
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservaDao {
    @Query("SELECT * FROM reservas ORDER BY id DESC")
    fun getAllReservas(): Flow<List<Reserva>>

    @Query("SELECT * FROM reservas WHERE id = :id")
    fun getReservaById(id: Int): Flow<Reserva>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReserva(reserva: Reserva)

    @Update
    suspend fun updateReserva(reserva: Reserva)

    @Delete
    suspend fun deleteReserva(reserva: Reserva)
}

