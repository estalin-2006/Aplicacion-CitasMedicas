package com.estalin.citasmedicasapp.ui.screens

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.estalin.citasmedicasapp.data.AppDatabase
import com.estalin.citasmedicasapp.data.entity.PacienteEntity
import com.estalin.citasmedicasapp.databinding.ActivityRegistroPacienteBinding
import kotlinx.coroutines.launch

class RegistroPacienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroPacienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroPacienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarBack.setNavigationOnClickListener {
            finish()
        }

        binding.btnRegistrar.setOnClickListener {
            registrarPaciente()
        }
    }

    private fun registrarPaciente() {

        val nombre = binding.etNombre.text.toString().trim()
        val apellido = binding.etApellido.text.toString().trim()
        val cedula = binding.etCedula.text.toString().trim()
        val telefono = binding.etTelefono.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()

        if (nombre.isEmpty() || cedula.isEmpty()) {
            Toast.makeText(this, "Nombre y cédula son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            db.pacienteDao().insert(
                PacienteEntity(
                    nombre = nombre,
                    apellido = apellido,
                    cedula = cedula,
                    telefono = telefono,
                    correo = email
                )
            )

            Toast.makeText(
                this@RegistroPacienteActivity,
                "Paciente registrado correctamente",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }
    }
}