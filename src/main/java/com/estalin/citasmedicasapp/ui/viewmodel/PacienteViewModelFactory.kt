package com.estalin.citasmedicasapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.estalin.citasmedicasapp.data.AppRepository

class PacienteViewModelFactory(
    private val repo: AppRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PacienteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PacienteViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}