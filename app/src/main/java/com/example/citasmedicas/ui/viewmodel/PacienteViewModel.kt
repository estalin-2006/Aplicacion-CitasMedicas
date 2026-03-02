package com.estalin.citasmedicasapp.ui.viewmodel

import androidx.lifecycle.*
import com.estalin.citasmedicasapp.data.AppRepository
import com.estalin.citasmedicasapp.data.entity.PacienteEntity
import kotlinx.coroutines.launch

class PacienteViewModel(private val repository: AppRepository) : ViewModel() {

    val allPacientes: LiveData<List<PacienteEntity>> = repository.allPacientes

    fun insert(paciente: PacienteEntity) = viewModelScope.launch {
        repository.insertPaciente(paciente)
    }

    fun update(paciente: PacienteEntity) = viewModelScope.launch {
        repository.updatePaciente(paciente)
    }

    fun delete(paciente: PacienteEntity) = viewModelScope.launch {
        repository.deletePaciente(paciente)
    }
}

class PacienteViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PacienteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PacienteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
