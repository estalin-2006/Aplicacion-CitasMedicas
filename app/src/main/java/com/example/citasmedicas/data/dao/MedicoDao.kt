package com.estalin.citasmedicasapp.data.dao

import androidx.room.*
import com.estalin.citasmedicasapp.data.entity.MedicoEntity

@Dao
interface MedicoDao {
    @Insert
    suspend fun insert(medico: MedicoEntity)

    @Update
    suspend fun update(medico: MedicoEntity)

    @Delete
    suspend fun delete(medico: MedicoEntity)

    @Query("SELECT * FROM medicos")
    suspend fun getAll(): List<MedicoEntity>

    @Query("SELECT * FROM medicos WHERE id = :id")
    suspend fun getById(id: Int): MedicoEntity?
}
