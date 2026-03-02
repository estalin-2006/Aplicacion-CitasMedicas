package com.estalin.citasmedicasapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historial")
data class HistorialEntity(
    @PrimaryKey(autoGenerate = true)
    val historialId: Int = 0,
    val pacienteId: Int,
    val medicoId: Int,
    val descripcion: String
)
