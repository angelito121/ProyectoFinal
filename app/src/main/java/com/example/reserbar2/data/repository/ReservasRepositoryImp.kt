package com.example.reserbar2.data.repository

import com.example.reserbar2.data.dao.ReservaDao
import com.example.reserbar2.data.model.Reserva

import kotlinx.coroutines.flow.Flow

class ReservasRepositoryImp(private val reservaDao: ReservaDao) : ReservasRepository {
    override fun getAllReservas(): Flow<List<Reserva>> = reservaDao.getAllReservas()

    override fun getReservaById(id: Int): Flow<Reserva> = reservaDao.getReservaById(id)

    override suspend fun insertReserva(reserva: Reserva) = reservaDao.insertReserva(reserva)

    override suspend fun updateReserva(reserva: Reserva) = reservaDao.updateReserva(reserva)

    override suspend fun deleteReserva(reserva: Reserva) = reservaDao.deleteReserva(reserva)
}