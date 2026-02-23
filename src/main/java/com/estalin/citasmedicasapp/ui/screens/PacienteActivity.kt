package com.estalin.citasmedicasapp.ui.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.estalin.citasmedicasapp.data.AppDatabase
import com.estalin.citasmedicasapp.data.AppRepository
import com.estalin.citasmedicasapp.databinding.ActivityPacienteBinding
import com.estalin.citasmedicasapp.ui.adapter.PacienteAdapter
import com.estalin.citasmedicasapp.ui.viewmodel.PacienteViewModel
import com.estalin.citasmedicasapp.data.NetworkState
import com.estalin.citasmedicasapp.ui.viewmodel.PacienteViewModelFactory

class PacienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPacienteBinding
    private lateinit var viewModel: PacienteViewModel
    private lateinit var adapter: PacienteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔹 ViewBinding
        binding = ActivityPacienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔹 Base de datos
        val db = AppDatabase.getDatabase(this)

        // 🔹 Repository
        val repo = AppRepository(
            db.usuarioDao(),
            db.pacienteDao(),
            db.medicoDao(),
            db.citaDao()
        )

        // 🔹 ViewModel
        viewModel = ViewModelProvider(
            this,
            PacienteViewModelFactory(repo)
        )[PacienteViewModel::class.java]

        // 🔹 RecyclerView
        adapter = PacienteAdapter()
        binding.recyclerPacientes.layoutManager =
            LinearLayoutManager(this)
        binding.recyclerPacientes.adapter = adapter

        // 🔹 Observador de datos
        viewModel.pacientes.observe(this) {
            adapter.submitList(it)
        }

        // 🔹 Botón guardar
        binding.btnGuardar.setOnClickListener {
            viewModel.guardar(
                binding.etNombre.text.toString(),
                binding.etCedula.text.toString(),
                binding.etTelefono.text.toString()
            )
        }

        // 🔹 Estado de red

    }
}