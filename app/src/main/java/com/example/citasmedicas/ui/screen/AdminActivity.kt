package com.estalin.citasmedicasapp.ui.screen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.estalin.citasmedicasapp.R
import com.estalin.citasmedicasapp.data.AppDatabase
import com.estalin.citasmedicasapp.data.AppRepository
import com.estalin.citasmedicasapp.data.RetrofitInstance
import kotlinx.coroutines.launch

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val db = AppDatabase.getDatabase(this)
        val repository = AppRepository(
            db.usuarioDao(), 
            db.pacienteDao(), 
            db.medicoDao(), 
            db.citaDao(), 
            RetrofitInstance.api
        )

        val btnGestionarPacientes = findViewById<Button>(R.id.btnGestionarPacientes)
        val btnSyncAll = findViewById<Button>(R.id.btnSyncAll)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnGestionarPacientes.setOnClickListener {
            startActivity(Intent(this, GestionPacientesActivity::class.java))
        }

        // --- BOTÓN DE SINCRONIZACIÓN ---
        btnSyncAll.setOnClickListener {
            lifecycleScope.launch {
                Toast.makeText(this@AdminActivity, "Sincronizando pacientes...", Toast.LENGTH_SHORT).show()
                val success = repository.sincronizarPacientesSupabase()
                if (success) {
                    Toast.makeText(this@AdminActivity, "¡Sincronización Exitosa!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@AdminActivity, "Fallo al sincronizar. Revisa Supabase.", Toast.LENGTH_LONG).show()
                }
            }
        }

        btnLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
