package com.estalin.citasmedicasapp.data.entity

import androidx.room.*

@Entity(
    tableName = "medicos",
    foreignKeys = [
        ForeignKey(
            entity = EspecialidadEntity::class,
            parentColumns = ["id"],
            childColumns = ["especialidadId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["especialidadId"])]
)
data class MedicoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val especialidadId: Int
)