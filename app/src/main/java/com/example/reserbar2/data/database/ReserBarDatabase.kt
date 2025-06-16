package com.example.reserbar2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.reserbar2.data.dao.MesaDao
import com.example.reserbar2.data.dao.ReservaDao
import com.example.reserbar2.data.model.Reserva
import com.example.reserbar2.data.model.Mesa

@Database(entities = [Reserva::class, Mesa::class], version = 2, exportSchema = false)
abstract class ReserBarDatabase : RoomDatabase() {

    abstract fun ReservaDao(): ReservaDao
    abstract fun MesaDao(): MesaDao

    companion object {
        @Volatile
        private var Instance: ReserBarDatabase? = null

        fun getDatabase(context: Context) : ReserBarDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ReserBarDatabase::class.java, "todo_database")
                    .fallbackToDestructiveMigration(false)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}