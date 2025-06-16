package com.example.reserbar2.data.dao

import androidx.room.*
import com.example.reserbar2.data.model.Mesa
import kotlinx.coroutines.flow.Flow

@Dao
interface MesaDao {
    @Query("SELECT * FROM mesa ORDER BY id DESC")
    fun getAllMesa(): Flow<List<Mesa>>

    @Query("SELECT * FROM mesa WHERE id = :id")
    fun getMesaById(id: Int): Flow<Mesa>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMesa(mesa: Mesa)

    @Update
    suspend fun updateMesa(mesa: Mesa)

    @Delete
    suspend fun deleteMesa(mesa: Mesa)
}