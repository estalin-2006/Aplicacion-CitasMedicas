package com.estalin.citasmedicasapp.data

import android.content.Context
import androidx.room.*
import com.estalin.citasmedicasapp.data.dao.*
import com.estalin.citasmedicasapp.data.entity.*

@Database(
    entities = [UsuarioEntity::class, PacienteEntity::class, MedicoEntity::class, CitaEntity::class, EspecialidadEntity::class],
    version = 4, // SUBIMOS LA VERSIÓN A 4 PARA LIMPIAR EL ERROR
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
                // ESTA LÍNEA ES CLAVE: Borra la DB vieja si el esquema cambió
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
