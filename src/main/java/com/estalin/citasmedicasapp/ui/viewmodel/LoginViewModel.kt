package com.estalin.citasmedicasapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.estalin.citasmedicasapp.data.AppRepository

class LoginViewModel(private val repo: AppRepository) : ViewModel() {

    suspend fun login(email: String, password: String) =
        repo.login(email, password)
}
