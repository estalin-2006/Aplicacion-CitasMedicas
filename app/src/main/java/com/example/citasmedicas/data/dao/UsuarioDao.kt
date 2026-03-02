package com.estalin.citasmedicasapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.estalin.citasmedicasapp.data.entity.UsuarioEntity

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insert(usuario: UsuarioEntity)

    @Query("SELECT * FROM usuarios WHERE rol = :rol")
    fun getUsersByRol(rol: String): LiveData<List<UsuarioEntity>>

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): UsuarioEntity?
}
