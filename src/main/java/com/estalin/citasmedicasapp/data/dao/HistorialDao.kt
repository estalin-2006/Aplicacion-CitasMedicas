package com.estalin.citasmedicasapp.data.dao

import androidx.room.*
import com.estalin.citasmedicasapp.data.entity.HistorialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistorialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(historial: HistorialEntity)

    @Query("SELECT * FROM historial")
    fun getAll(): Flow<List<HistorialEntity>>
}
