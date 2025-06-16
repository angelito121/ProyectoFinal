package com.example.reserbar2.data.repository


import com.example.reserbar2.data.model.Mesa
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Task] from a given data source.
 */
interface MesasRepository {
    fun getAllMesa(): Flow<List<Mesa>>

    fun getMesaById(id: Int): Flow<Mesa>

    suspend fun insertMesa(reserva: Mesa)

    suspend fun updateMesa(reserva: Mesa)

    suspend fun deleteMesa(reserva: Mesa)
}