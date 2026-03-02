package com.estalin.citasmedicasapp.data.dao
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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
}