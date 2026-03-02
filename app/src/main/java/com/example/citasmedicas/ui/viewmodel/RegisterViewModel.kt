package com.estalin.citasmedicasapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.estalin.citasmedicasapp.data.AppRepository
import com.estalin.citasmedicasapp.data.entity.UsuarioEntity

class RegisterViewModel(private val repo: AppRepository) : ViewModel() {
    suspend fun register(usuario: UsuarioEntity): Boolean {
        return repo.register(usuario)
    }
}

class RegisterViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
