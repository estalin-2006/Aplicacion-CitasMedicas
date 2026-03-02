package com.estalin.citasmedicasapp.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.estalin.citasmedicasapp.R
import com.estalin.citasmedicasapp.data.AppDatabase
import com.estalin.citasmedicasapp.data.AppRepository
import com.estalin.citasmedicasapp.data.ApiService
import com.estalin.citasmedicasapp.ui.viewmodel.LoginViewModel
import com.estalin.citasmedicasapp.ui.viewmodel.LoginViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val db = AppDatabase.getDatabase(this)

        // 🔹 Crear Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/") // ejemplo
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val repo = AppRepository(
            db.usuarioDao(),
            db.pacienteDao(),
            db.medicoDao(),
            db.citaDao()
              // 🔥 ahora sí enviamos apiService
        )

        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(repo)
        )[LoginViewModel::class.java]
    }
}