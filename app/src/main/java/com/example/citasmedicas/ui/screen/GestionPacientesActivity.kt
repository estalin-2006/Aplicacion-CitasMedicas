package com.estalin.citasmedicasapp.ui.screen

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estalin.citasmedicasapp.R
import com.estalin.citasmedicasapp.data.AppDatabase
import com.estalin.citasmedicasapp.data.AppRepository
import com.estalin.citasmedicasapp.data.RetrofitInstance
import com.estalin.citasmedicasapp.data.entity.PacienteEntity
import com.estalin.citasmedicasapp.ui.adapter.PacienteAdapter
import com.estalin.citasmedicasapp.ui.viewmodel.PacienteViewModel
import com.estalin.citasmedicasapp.ui.viewmodel.PacienteViewModelFactory
import kotlinx.coroutines.launch

class GestionPacientesActivity : AppCompatActivity() {

    private lateinit var viewModel: PacienteViewModel
    private lateinit var adapter: PacienteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_pacientes)

        val db = AppDatabase.getDatabase(this)
        val repo = AppRepository(
            db.usuarioDao(), 
            db.pacienteDao(), 
            db.medicoDao(), 
            db.citaDao(), 
            RetrofitInstance.api
        )
        viewModel = ViewModelProvider(this, PacienteViewModelFactory(repo))[PacienteViewModel::class.java]

        val rvPacientes = findViewById<RecyclerView>(R.id.rvPacientes)
        val btnAddPatient = findViewById<Button>(R.id.btnAddPatient)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnSync = findViewById<Button>(R.id.btnSync)

        btnBack.setOnClickListener { finish() }

        adapter = PacienteAdapter(emptyList()) { paciente ->
            Toast.makeText(this, "Editar: ${paciente.nombre}", Toast.LENGTH_SHORT).show()
        }

        rvPacientes.layoutManager = LinearLayoutManager(this)
        rvPacientes.adapter = adapter

        viewModel.allPacientes.observe(this) { pacientes ->
            adapter.updateData(pacientes)
        }

        btnAddPatient.setOnClickListener {
            // Actualizado para coincidir con la entidad: nombre, email, password, rol
            val nuevo = PacienteEntity(
                nombre = "Paciente de Prueba",
                email = "prueba@correo.com",
                password = "123",
                rol = "PACIENTE"
            )
            viewModel.insert(nuevo)
            Toast.makeText(this, "Paciente añadido y subiendo a Supabase...", Toast.LENGTH_SHORT).show()
        }

        btnSync.setOnClickListener {
            lifecycleScope.launch {
                val success = viewModel.sincronizarConSupabase()
                if (success) {
                    Toast.makeText(this@GestionPacientesActivity, "¡Sincronización completa!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@GestionPacientesActivity, "Error al sincronizar con la nube", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
