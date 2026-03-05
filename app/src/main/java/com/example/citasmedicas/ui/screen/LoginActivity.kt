package com.estalin.citasmedicasapp.ui.screen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.estalin.citasmedicasapp.R
import com.estalin.citasmedicasapp.data.AppDatabase
import com.estalin.citasmedicasapp.data.AppRepository
import com.estalin.citasmedicasapp.data.RetrofitInstance
import com.estalin.citasmedicasapp.ui.viewmodel.LoginViewModel
import com.estalin.citasmedicasapp.ui.viewmodel.LoginViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val db = AppDatabase.getDatabase(this)
        // Corregido: Se pasan los 5 parámetros requeridos por el constructor de AppRepository
        val repository = AppRepository(
            db.usuarioDao(), 
            db.pacienteDao(), 
            db.medicoDao(), 
            db.citaDao(), 
            RetrofitInstance.api
        )
        viewModel = ViewModelProvider(this, LoginViewModelFactory(repository))[LoginViewModel::class.java]

        val emailField = findViewById<TextInputEditText>(R.id.etEmail)
        val passwordField = findViewById<TextInputEditText>(R.id.etPassword)
        val loginBtn = findViewById<Button>(R.id.btnLogin)
        val registerBtn = findViewById<Button>(R.id.btnGoToRegister)

        registerBtn?.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        loginBtn.setOnClickListener {
            val emailText = emailField.text.toString().trim()
            val passText = passwordField.text.toString().trim()

            if (emailText.isEmpty() || passText.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val user = viewModel.login(emailText, passText)
                    if (user != null) {
                        Toast.makeText(this@LoginActivity, "¡Bienvenido ${user.nombre}!", Toast.LENGTH_SHORT).show()
                        
                        // Lógica de redirección según el ROL
                        val intent = when (user.rol) {
                            "ADMIN" -> Intent(this@LoginActivity, AdminActivity::class.java)
                            "MEDICO" -> Intent(this@LoginActivity, MedicoActivity::class.java)
                            else -> Intent(this@LoginActivity, PacienteActivity::class.java)
                        }
                        
                        startActivity(intent)
                        finish() 
                        
                    } else {
                        Toast.makeText(this@LoginActivity, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "Error al conectar con la base de datos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
