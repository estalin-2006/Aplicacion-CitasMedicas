package com.estalin.citasmedicasapp.ui.screen

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.estalin.citasmedicasapp.R
import com.estalin.citasmedicasapp.data.AppDatabase
import com.estalin.citasmedicasapp.data.entity.CitaEntity
import com.estalin.citasmedicasapp.data.entity.UsuarioEntity
import kotlinx.coroutines.launch
import java.util.*

class AgendarCitaActivity : AppCompatActivity() {

    private var selectedDate = ""
    private var selectedMedicoId = -1
    private var medicosList = listOf<UsuarioEntity>()
    private val todasLasHoras = listOf("08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar_cita)

        val spMedicos = findViewById<Spinner>(R.id.spMedicos)
        val btnSelectDate = findViewById<Button>(R.id.btnSelectDate)
        val tvSelectedDate = findViewById<TextView>(R.id.tvSelectedDate)
        val spHoras = findViewById<Spinner>(R.id.spHoras)
        val tvHoraLabel = findViewById<TextView>(R.id.tvHoraLabel)
        val btnConfirmarCita = findViewById<Button>(R.id.btnConfirmarCita)

        val db = AppDatabase.getDatabase(this)
        
        // 1. Cargar Médicos
        db.usuarioDao().getUsersByRol("MEDICO").observe(this) { lista ->
            medicosList = lista
            val nombres = medicosList.map { "Dr. ${it.nombre} - ${it.especialidad ?: "Gral"}" }
            spMedicos.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nombres)
        }

        // 2. Elegir Fecha y Consultar Disponibilidad
        btnSelectDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, day ->
                selectedDate = "$day/${month + 1}/$year"
                tvSelectedDate.text = "Fecha seleccionada: $selectedDate"
                
                // Buscar disponibilidad
                val pos = spMedicos.selectedItemPosition
                if (pos >= 0) {
                    selectedMedicoId = medicosList[pos].id
                    consultarHorasDisponibles(db, selectedMedicoId, selectedDate, spHoras, tvHoraLabel)
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).apply {
                datePicker.minDate = System.currentTimeMillis() - 1000
                show()
            }
        }

        // 3. Confirmar
        btnConfirmarCita.setOnClickListener {
            if (selectedDate.isEmpty() || spHoras.selectedItem == null) {
                Toast.makeText(this, "Complete fecha y hora", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val prefs = getSharedPreferences("session", MODE_PRIVATE)
            val pacienteId = prefs.getInt("userId", -1)

            lifecycleScope.launch {
                val nuevaCita = CitaEntity(
                    fecha = selectedDate,
                    hora = spHoras.selectedItem.toString(),
                    pacienteId = pacienteId,
                    medicoId = selectedMedicoId
                )
                db.citaDao().insert(nuevaCita)
                Toast.makeText(this@AgendarCitaActivity, "Cita agendada para las ${nuevaCita.hora}", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun consultarHorasDisponibles(db: AppDatabase, medicoId: Int, fecha: String, spinner: Spinner, label: TextView) {
        // En un entorno LiveData, filtramos las horas que ya existen en la base de datos
        db.citaDao().getAll().observe(this) { todasCitas ->
            val horasOcupadas = todasCitas
                .filter { it.medicoId == medicoId && it.fecha == fecha && it.estado != "CANCELADA" }
                .map { it.hora }

            val horasDisponibles = todasLasHoras.filter { it !in horasOcupadas }

            if (horasDisponibles.isNotEmpty()) {
                spinner.visibility = View.VISIBLE
                label.visibility = View.VISIBLE
                spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, horasDisponibles)
            } else {
                spinner.visibility = View.GONE
                label.visibility = View.GONE
                Toast.makeText(this, "No hay horas disponibles para este día", Toast.LENGTH_LONG).show()
            }
        }
    }
}
