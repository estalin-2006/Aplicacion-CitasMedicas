package com.estalin.citasmedicasapp.data.dao

import androidx.room.*
import com.estalin.citasmedicasapp.data.entity.CitaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CitaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cita: CitaEntity)

    @Query("SELECT * FROM citas")
    fun getAll(): Flow<List<CitaEntity>>
}
