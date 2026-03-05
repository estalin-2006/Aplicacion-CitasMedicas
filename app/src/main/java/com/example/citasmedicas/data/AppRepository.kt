package com.estalin.citasmedicasapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.estalin.citasmedicasapp.data.dao.*
import com.estalin.citasmedicasapp.data.entity.*

class AppRepository(
    val usuarioDao: UsuarioDao,
    val pacienteDao: PacienteDao,
    val medicoDao: MedicoDao,
    val citaDao: CitaDao,
    val apiService: ApiService
) {

    // --- Usuarios ---
    suspend fun register(usuario: UsuarioEntity): Boolean {
        val existente = usuarioDao.getByEmail(usuario.email)
        if (existente == null) {
            usuarioDao.insert(usuario)
            return try {
                val response = apiService.insertarUsuario(usuario)
                if (!response.isSuccessful) {
                    Log.e("SUPABASE_ERROR", "Error Usuario: ${response.errorBody()?.string()}")
                }
                response.isSuccessful
            } catch (e: Exception) {
                Log.e("SUPABASE_ERROR", "Fallo Red Usuario: ${e.message}")
                true 
            }
        }
        return false
    }

    suspend fun login(email: String, password: String): UsuarioEntity? {
        return usuarioDao.login(email, password)
    }

    // --- Pacientes ---
    val allPacientes: LiveData<List<PacienteEntity>> = pacienteDao.getAll()

    suspend fun insertPaciente(paciente: PacienteEntity) {
        // 1. Guardar local
        pacienteDao.insert(paciente)
        
        // 2. Intentar guardar en Supabase con logs de error
        try {
            val response = apiService.insertarPaciente(paciente)
            if (response.isSuccessful) {
                Log.d("SUPABASE_SYNC", "Paciente ${paciente.nombre} subido con éxito")
            } else {
                val errorMsg = response.errorBody()?.string()
                Log.e("SUPABASE_ERROR", "Código: ${response.code()} - Error: $errorMsg")
            }
        } catch (e: Exception) {
            Log.e("SUPABASE_ERROR", "Error de conexión: ${e.message}")
        }
    }

    suspend fun updatePaciente(paciente: PacienteEntity) {
        pacienteDao.update(paciente)
    }

    suspend fun deletePaciente(paciente: PacienteEntity) {
        pacienteDao.delete(paciente)
    }

    suspend fun sincronizarPacientesSupabase(): Boolean {
        return try {
            val locales = pacienteDao.getAllSync()
            if (locales.isEmpty()) return true
            var exitoTotal = true
            for (p in locales) {
                val res = apiService.insertarPaciente(p)
                if (!res.isSuccessful) exitoTotal = false
            }
            exitoTotal
        } catch (e: Exception) {
            false
        }
    }

    // --- Médicos ---
    suspend fun insertMedico(medico: MedicoEntity): Boolean {
        medicoDao.insert(medico)
        return try {
            val response = apiService.insertarMedico(medico)
            response.isSuccessful
        } catch (e: Exception) {
            true
        }
    }

    // --- Citas ---
    suspend fun insertCita(cita: CitaEntity) {
        citaDao.insert(cita)
        try {
            apiService.insertarCita(cita)
        } catch (e: Exception) { }
    }
}
