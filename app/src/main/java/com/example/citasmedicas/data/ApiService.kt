package com.estalin.citasmedicasapp.data

import com.estalin.citasmedicasapp.data.entity.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("usuarios")
    suspend fun insertarUsuario(@Body usuario: UsuarioEntity): Response<Unit>

    @POST("pacientes")
    suspend fun insertarPaciente(@Body paciente: PacienteEntity): Response<Unit>

    @POST("medicos")
    suspend fun insertarMedico(@Body medico: MedicoEntity): Response<Unit>

    @POST("citas")
    suspend fun insertarCita(@Body cita: CitaEntity): Response<Unit>
}
