package com.estalin.citasmedicasapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.estalin.citasmedicasapp.data.dao.*
import com.estalin.citasmedicasapp.data.entity.*

@Database(
    entities = [
        UsuarioEntity::class,
        PacienteEntity::class,
        MedicoEntity::class,
        EspecialidadEntity::class,
        CitaEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun pacienteDao(): PacienteDao
    abstract fun medicoDao(): MedicoDao
    abstract fun citaDao(): CitaDao
    abstract fun especialidadDao(): EspecialidadDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "citas_medicas_db"
                )
                .fallbackToDestructiveMigration() // ESTO EVITA QUE LA APP SE CIERRE POR CAMBIOS EN TABLAS
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
