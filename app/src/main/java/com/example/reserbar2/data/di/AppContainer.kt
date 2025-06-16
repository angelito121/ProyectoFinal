package com.example.reserbar2.data.di

import android.content.Context
import com.example.reserbar2.data.database.ReserBarDatabase
import com.example.reserbar2.data.repository.MesasRepository
import com.example.reserbar2.data.repository.MesasRepositoryImp
import com.example.reserbar2.data.repository.ReservasRepository
import com.example.reserbar2.data.repository.ReservasRepositoryImp

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val reservasRepository: ReservasRepository
    val mesasRepository: MesasRepository
}

/**
 * [AppContainer] implementation that provides instance of [ReservasRepositoryImp]
 */
class DefaultAppContainer(private val context: Context
) : AppContainer {
    /**
     * Implementation for [ReservasRepository]
     */
    override val reservasRepository: ReservasRepository by lazy {
        ReservasRepositoryImp(ReserBarDatabase.getDatabase(context).ReservaDao())
    }
    override val mesasRepository: MesasRepository by lazy {
        MesasRepositoryImp(ReserBarDatabase.getDatabase(context).MesaDao())
    }

}