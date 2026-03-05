package com.estalin.citasmedicasapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    @Expose(serialize = false) // No enviar ID a Supabase
    val id: Int = 0,
    
    @Expose
    val nombre: String,
    
    @Expose
    val email: String,
    
    @Expose
    val password: String,
    
    @Expose
    val rol: String,
    
    @Expose
    val especialidad: String? = null,
    
    @Expose
    val codigo: String? = null
)
