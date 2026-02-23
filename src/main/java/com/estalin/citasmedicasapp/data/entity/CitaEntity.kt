package com.estalin.citasmedicasapp.data.entity

import androidx.room.*

@Entity(
    tableName = "citas",
    foreignKeys = [
        ForeignKey(
            entity = PacienteEntity::class,
            parentColumns = ["pacienteId"],
            childColumns = ["pacienteId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MedicoEntity::class,
            parentColumns = ["medicoId"],
            childColumns = ["medicoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("pacienteId"), Index("medicoId")]
)
data class CitaEntity(
    @PrimaryKey(autoGenerate = true)
    val citaId: Int = 0,
    val pacienteId: Int,
    val medicoId: Int,
    val fecha: String,
    val hora: String
)
