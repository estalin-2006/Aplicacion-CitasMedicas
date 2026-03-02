package com.estalin.citasmedicasapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.estalin.citasmedicasapp.data.entity.CitaConDoctor
import com.estalin.citasmedicasapp.data.entity.CitaEntity

@Dao
interface CitaDao {
    @Insert
    suspend fun insert(cita: CitaEntity)

    @Update
    suspend fun update(cita: CitaEntity)

    @Transaction
    @Query("SELECT * FROM citas WHERE pacienteId = :pacienteId")
    fun getCitasConDoctorByPaciente(pacienteId: Int): LiveData<List<CitaConDoctor>>

    @Transaction
    @Query("SELECT * FROM citas WHERE medicoId = :medicoId")
    fun getCitasConPacienteByMedico(medicoId: Int): LiveData<List<CitaConDoctor>>

    @Query("SELECT * FROM citas")
    fun getAll(): LiveData<List<CitaEntity>>
}
