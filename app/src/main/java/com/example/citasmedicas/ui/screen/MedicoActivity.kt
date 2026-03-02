package com.estalin.citasmedicasapp.ui.screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estalin.citasmedicasapp.R
import com.estalin.citasmedicasapp.data.AppDatabase
import com.estalin.citasmedicasapp.ui.adapter.CitaAdapter
import kotlinx.coroutines.launch

class MedicoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medico)

        val tvMedicoName = findViewById<TextView>(R.id.tvMedicoName)
        val tvEspecialidad = findViewById<TextView>(R.id.tvEspecialidad)
        val btnViewAppointments = findViewById<Button>(R.id.btnViewAppointments)
        val btnLogout = findViewById<ImageButton>(R.id.btnLogout)
        
        val tvSectionTitle = findViewById<TextView>(R.id.tvSectionTitle)
        val rvCitas = findViewById<RecyclerView>(R.id.rvCitas)

        val prefs = getSharedPreferences("session", MODE_PRIVATE)
        val medicoId = prefs.getInt("userId", -1)
        val nombre = prefs.getString("nombre", "Médico")
        val especialidad = prefs.getString("especialidad", "Médico General")

        tvMedicoName.text = "Bienvenido, Dr. $nombre"
        tvEspecialidad.text = "Especialidad: $especialidad"

        val db = AppDatabase.getDatabase(this)

        btnViewAppointments.setOnClickListener {
            tvSectionTitle.visibility = View.VISIBLE
            rvCitas.visibility = View.VISIBLE
            
            rvCitas.layoutManager = LinearLayoutManager(this)
            
            // Usamos la nueva consulta que trae la información del paciente
            db.citaDao().getCitasConPacienteByMedico(medicoId).observe(this) { listaCitasConDoctor ->
                val adapter = CitaAdapter(listaCitasConDoctor, showStatusSelector = true) { item, nuevoEstado ->
                    // Actualizamos el estado en la base de datos
                    lifecycleScope.launch {
                        val citaActualizada = item.cita.copy(estado = nuevoEstado)
                        db.citaDao().update(citaActualizada)
                        Toast.makeText(this@MedicoActivity, "Cita marcada como $nuevoEstado", Toast.LENGTH_SHORT).show()
                    }
                }
                rvCitas.adapter = adapter
            }
        }

        btnLogout.setOnClickListener {
            prefs.edit().clear().apply()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
