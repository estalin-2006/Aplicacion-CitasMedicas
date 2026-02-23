package com.estalin.citasmedicasapp.ui.viewmodel

import androidx.lifecycle.*
import com.estalin.citasmedicasapp.data.*
import com.estalin.citasmedicasapp.data.entity.*
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData
class PacienteViewModel(
    private val repo: AppRepository
) : ViewModel() {

    val pacientes: LiveData<List<PacienteEntity>> =
        repo.getPacientes()

    fun guardar(nombre: String, cedula: String, telefono: String) {
        viewModelScope.launch {
            repo.insertPaciente(
                PacienteEntity(
                    nombre = nombre,
                    apellido = "",
                    cedula = cedula,
                    telefono = telefono,
                    correo = ""
                )
            )
        }
    }
}