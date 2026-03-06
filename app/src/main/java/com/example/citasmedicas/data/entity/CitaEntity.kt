package com.estalin.citasmedicasapp.data.entity

import androidx.room.*

@Entity(
    tableName = "citas",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["pacienteId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["medicoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["pacienteId"]),
        Index(value = ["medicoId"])
    ]
)
data class CitaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fecha: String,
    val hora: String,
    val pacienteId: Int,
    val medicoId: Int,
    val estado: String = "PENDIENTE" // PENDIENTE, ATENDIDA, CANCELADA
)
