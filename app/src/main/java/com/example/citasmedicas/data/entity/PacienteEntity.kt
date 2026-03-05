package com.estalin.citasmedicasapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

@Entity(tableName = "pacientes")
data class PacienteEntity(
    @PrimaryKey(autoGenerate = true)
    @Expose(serialize = false) // ESTO HACE QUE NO SE ENVÍE A SUPABASE
    val id: Int = 0,
    val nombre: String,
    val email: String,
    val password: String,
    val rol: String = "PACIENTE"
)
