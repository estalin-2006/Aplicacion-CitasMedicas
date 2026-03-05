package com.estalin.citasmedicasapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicos")
data class MedicoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val email: String, // Correo
    val rol: String = "MEDICO",
    val especialidad: String
)
