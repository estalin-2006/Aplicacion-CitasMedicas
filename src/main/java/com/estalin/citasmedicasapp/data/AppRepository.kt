package com.estalin.citasmedicasapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.estalin.citasmedicasapp.data.dao.*
import com.estalin.citasmedicasapp.data.entity.*

class AppRepository(
    private val usuarioDao: UsuarioDao,
    private val pacienteDao: PacienteDao,
    private val medicoDao: MedicoDao,
    private val citaDao: CitaDao
) {

    fun getPacientes(): LiveData<List<PacienteEntity>> {
        return pacienteDao.getAll().asLiveData()
    }

    suspend fun login(email: String, password: String): UsuarioEntity? {
        return usuarioDao.login(email, password)
    }

    suspend fun insertPaciente(paciente: PacienteEntity) {
        pacienteDao.insert(paciente)
    }
}