package com.estalin.citasmedicasapp.data.dao

import androidx.room.*
import com.estalin.citasmedicasapp.data.entity.MedicoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medico: MedicoEntity)

    @Query("SELECT * FROM medicos")
    fun getAll(): Flow<List<MedicoEntity>>
}
