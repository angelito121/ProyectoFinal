package com.example.reserbar2.data.repository

import com.example.reserbar2.data.dao.MesaDao
import com.example.reserbar2.data.model.Mesa
import kotlinx.coroutines.flow.Flow


class MesasRepositoryImp(private val mesaDao: MesaDao) : MesasRepository {
    override fun getAllMesa(): Flow<List<Mesa>> = mesaDao.getAllMesa()

    override fun getMesaById(id: Int): Flow<Mesa> = mesaDao.getMesaById(id)

    override suspend fun insertMesa(mesa: Mesa) = mesaDao.insertMesa(mesa)

    override suspend fun updateMesa(mesa: Mesa) = mesaDao.updateMesa(mesa)

    override suspend fun deleteMesa(mesa: Mesa) = mesaDao.deleteMesa(mesa)
}