package com.estalin.citasmedicasapp.ui.screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estalin.citasmedicasapp.R
import com.estalin.citasmedicasapp.data.AppDatabase
import com.estalin.citasmedicasapp.ui.adapter.CitaAdapter

class PacienteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paciente)

        val tvPacienteName = findViewById<TextView>(R.id.tvPacienteName)
        val btnScheduleAppointment = findViewById<Button>(R.id.btnScheduleAppointment)
        val btnViewMyAppointments = findViewById<Button>(R.id.btnViewMyAppointments)
        val btnLogout = findViewById<ImageButton>(R.id.btnLogout)
        
        val tvSectionTitle = findViewById<TextView>(R.id.tvSectionTitle)
        val rvCitasPaciente = findViewById<RecyclerView>(R.id.rvCitasPaciente)

        val prefs = getSharedPreferences("session", MODE_PRIVATE)
        val pacienteId = prefs.getInt("userId", -1)
        val nombre = prefs.getString("nombre", "Paciente")
        tvPacienteName.text = "Bienvenido, $nombre"

        // 1. Navegar a Agendar Cita
        btnScheduleAppointment.setOnClickListener {
            startActivity(Intent(this, AgendarCitaActivity::class.java))
        }

        // 2. Ver mis Citas (usando la nueva consulta con la información del Doctor)
        btnViewMyAppointments.setOnClickListener {
            tvSectionTitle.visibility = View.VISIBLE
            rvCitasPaciente.visibility = View.VISIBLE
            
            val db = AppDatabase.getDatabase(this)
            rvCitasPaciente.layoutManager = LinearLayoutManager(this)
            
            // Usamos la nueva consulta que une Cita y Doctor
            db.citaDao().getCitasConDoctorByPaciente(pacienteId).observe(this) { listaCitasConDoctor ->
                // Pasamos `false` para que el paciente no vea el selector de estado
                val adapter = CitaAdapter(listaCitasConDoctor, showStatusSelector = false) { _, _ ->
                    // Acción de cambio de estado vacía para el paciente
                }
                rvCitasPaciente.adapter = adapter
            }
        }

        // 3. Cerrar Sesión
        btnLogout.setOnClickListener {
            prefs.edit().clear().apply()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
