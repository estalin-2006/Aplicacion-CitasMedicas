package com.estalin.citasmedicasapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicos")
data class MedicoEntity(
    @PrimaryKey(autoGenerate = true)
    val medicoId: Int = 0,
    val nombre: String,
    val especialidadId: Int
)
