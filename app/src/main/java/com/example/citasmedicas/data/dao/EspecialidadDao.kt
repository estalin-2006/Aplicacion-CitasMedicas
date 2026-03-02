package com.estalin.citasmedicasapp.data.dao

import androidx.room.*
import com.estalin.citasmedicasapp.data.entity.EspecialidadEntity

@Dao
interface EspecialidadDao {
    @Insert
    suspend fun insert(especialidad: EspecialidadEntity)

    @Update
    suspend fun update(especialidad: EspecialidadEntity)

    @Delete
    suspend fun delete(especialidad: EspecialidadEntity)

    @Query("SELECT * FROM especialidades")
    suspend fun getAll(): List<EspecialidadEntity>
}
