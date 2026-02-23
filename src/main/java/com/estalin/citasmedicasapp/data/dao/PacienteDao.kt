package com.estalin.citasmedicasapp.data.dao

import androidx.room.*
import com.estalin.citasmedicasapp.data.entity.PacienteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PacienteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(paciente: PacienteEntity)

    @Update
    suspend fun update(paciente: PacienteEntity)

    @Delete
    suspend fun delete(paciente: PacienteEntity)

    @Query("SELECT * FROM pacientes")
    fun getAll(): Flow<List<PacienteEntity>>
}
