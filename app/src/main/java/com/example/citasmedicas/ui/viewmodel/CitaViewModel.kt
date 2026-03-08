package com.example.citasmedicas.ui.viewmodel

import androidx.lifecycle.*
import com.example.citasmedicas.data.AppRepository
import com.example.citasmedicas.data.entity.CitaEntity
import kotlinx.coroutines.launch

class CitaViewModel(private val repository: AppRepository) : ViewModel() {

    val allCitas: LiveData<List<CitaEntity>> = repository.allCitas

    fun insert(cita: CitaEntity) = viewModelScope.launch {
        repository.insertCita(cita)
    }
}

class CitaViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CitaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CitaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}