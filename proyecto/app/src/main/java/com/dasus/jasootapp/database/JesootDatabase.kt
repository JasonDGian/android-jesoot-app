package com.dasus.jasootapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dasus.jasootapp.dao.PreguntaDao
import com.dasus.jasootapp.models.Pregunta

@Database(entities = [Pregunta::class], version = 1)
abstract class JesootDatabase: RoomDatabase() {
    // Repositorio DAO de pregunta.
    abstract fun preguntaDao(): PreguntaDao

    // Companion object para patron SINGLETON
    companion object {
        @Volatile
        private var INSTANCE: JesootDatabase? = null

        fun getDatabase(context: Context): JesootDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JesootDatabase::class.java,
                    "jesoot_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
