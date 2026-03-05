package com.estalin.citasmedicasapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.estalin.citasmedicasapp.data.entity.PacienteEntity

@Dao
interface PacienteDao {

    @Insert
    suspend fun insert(paciente: PacienteEntity)

    @Update
    suspend fun update(paciente: PacienteEntity)

    @Delete
    suspend fun delete(paciente: PacienteEntity)

    @Query("SELECT * FROM pacientes")
    fun getAll(): LiveData<List<PacienteEntity>>

    @Query("SELECT * FROM pacientes")
    suspend fun getAllSync(): List<PacienteEntity>
}
