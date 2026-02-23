package com.estalin.citasmedicasapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(

    @PrimaryKey(autoGenerate = true)
    val usuarioId: Int = 0,

    val nombre: String,

    val email: String,

    val password: String,

    val rol: String // "PACIENTE" o "MEDICO"
)

