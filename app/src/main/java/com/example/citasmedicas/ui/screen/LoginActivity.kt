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
import com.estalin.citasmedicasapp.data.entity.UsuarioEntity
import com.estalin.citasmedicasapp.ui.viewmodel.LoginViewModel
import com.estalin.citasmedicasapp.ui.viewmodel.LoginViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        verificarSesion()

        val db = AppDatabase.getDatabase(this)
        val repo = AppRepository(db.usuarioDao(), db.pacienteDao(), RetrofitInstance.api)
        viewModel = ViewModelProvider(this, LoginViewModelFactory(repo))[LoginViewModel::class.java]

        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnGoToRegister = findViewById<Button>(R.id.btnGoToRegister)

        btnGoToRegister?.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val email = etEmail?.text.toString().trim()
            val password = etPassword?.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, complete los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val usuario = viewModel.login(email, password)
                    if (usuario != null) {
                        guardarSesion(usuario)
                        dirigirSegunRol(usuario.rol)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "Error al acceder a la base de datos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun dirigirSegunRol(rol: String) {
        val intent = when (rol.uppercase()) {
            "ADMIN" -> Intent(this, AdminActivity::class.java)
            "MEDICO" -> Intent(this, MedicoActivity::class.java)
            "PACIENTE" -> Intent(this, PacienteActivity::class.java)
            else -> null
        }
        intent?.let {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    private fun guardarSesion(usuario: UsuarioEntity) {
        val prefs = getSharedPreferences("session", MODE_PRIVATE)
        prefs.edit().apply {
            putBoolean("isLogged", true)
            putInt("userId", usuario.id) // importante Guardar el ID
            putString("rol", usuario.rol)
            putString("nombre", usuario.nombre)
            putString("especialidad", usuario.especialidad)
            apply()
        }
    }

    private fun verificarSesion() {
        val prefs = getSharedPreferences("session", MODE_PRIVATE)
        if (prefs.getBoolean("isLogged", false)) {
            dirigirSegunRol(prefs.getString("rol", "") ?: "")
            finish()
        }
    }
}
