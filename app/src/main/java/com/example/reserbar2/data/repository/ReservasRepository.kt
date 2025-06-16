package com.example.reserbar2.data.repository

import com.example.reserbar2.data.model.Reserva
import kotlinx.coroutines.flow.Flow

interface ReservasRepository {
    fun getAllReservas(): Flow<List<Reserva>>

    fun getReservaById(id: Int): Flow<Reserva>

    suspend fun insertReserva(reserva: Reserva)

    suspend fun updateReserva(reserva: Reserva)

    suspend fun deleteReserva(reserva: Reserva)
}