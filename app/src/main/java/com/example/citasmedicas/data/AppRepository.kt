package com.estalin.citasmedicasapp.data

import androidx.lifecycle.LiveData
import com.estalin.citasmedicasapp.data.dao.*
import com.estalin.citasmedicasapp.data.entity.*

class AppRepository(
    private val usuarioDao: UsuarioDao,
    private val pacienteDao: PacienteDao,
    private val apiService: ApiService // Añadimos ApiService
) {

    // --- Usuarios ---
    suspend fun register(usuario: UsuarioEntity): Boolean {
        val existente = usuarioDao.getByEmail(usuario.email)
        return if (existente == null) {
            usuarioDao.insert(usuario)
            true
        } else {
            false
        }
    }

    suspend fun login(email: String, password: String): UsuarioEntity? {
        return usuarioDao.login(email, password)
    }

    // --- Pacientes (CRUD Offline) ---
    val allPacientes: LiveData<List<PacienteEntity>> = pacienteDao.getAll()

    suspend fun insertPaciente(paciente: PacienteEntity) {
        pacienteDao.insert(paciente)
    }

    suspend fun updatePaciente(paciente: PacienteEntity) {
        pacienteDao.update(paciente)
    }

    suspend fun deletePaciente(paciente: PacienteEntity) {
        pacienteDao.delete(paciente)
    }

    // --- API (Online) ---
    suspend fun getExternalData(): List<Post> {
        return apiService.getPosts()
    }
}
