package com.estalin.citasmedicasapp.data.entity

import androidx.room.Embedded
import androidx.room.Relation

// Esta clase permite cargar la cita junto con los datos del usuario (Médico o Paciente)
data class CitaConDoctor(
    @Embedded val cita: CitaEntity,
    @Relation(
        parentColumn = "medicoId",
        entityColumn = "id"
    )
    val medico: UsuarioEntity,
    @Relation(
        parentColumn = "pacienteId",
        entityColumn = "id"
    )
    val paciente: UsuarioEntity
)
