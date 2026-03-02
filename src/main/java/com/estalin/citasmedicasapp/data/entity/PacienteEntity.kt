package com.estalin.citasmedicasapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pacientes")
data class PacienteEntity(
    @PrimaryKey(autoGenerate = true)
    val pacienteId: Int = 0,
    val nombre: String,
    val apellido: String,
    val cedula: String,
    val telefono: String,
    val correo: String
)
