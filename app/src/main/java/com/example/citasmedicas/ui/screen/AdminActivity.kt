package com.estalin.citasmedicasapp.ui.screen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estalin.citasmedicasapp.R
import com.estalin.citasmedicasapp.data.AppDatabase
import com.estalin.citasmedicasapp.data.AppRepository
import com.estalin.citasmedicasapp.data.RetrofitInstance
import com.estalin.citasmedicasapp.ui.adapter.UsuarioAdapter
import com.google.android.material.card.MaterialCardView

class AdminActivity : AppCompatActivity() {

    private lateinit var usuarioAdapter: UsuarioAdapter
    private lateinit var rvDynamicList: RecyclerView
    private lateinit var tvSectionTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        rvDynamicList = findViewById(R.id.rvDynamicList)
        tvSectionTitle = findViewById(R.id.tvSectionTitle)
        val btnManagePatients = findViewById<MaterialCardView>(R.id.btnManagePatients)
        val btnManageMedicos = findViewById<MaterialCardView>(R.id.btnManageMedicos)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        val db = AppDatabase.getDatabase(this)
        
        // Configuramos el RecyclerView con el callback de edición
        usuarioAdapter = UsuarioAdapter(emptyList()) { usuario ->
            Toast.makeText(this, "Editar: ${usuario.nombre}", Toast.LENGTH_SHORT).show()
        }
        
        rvDynamicList.layoutManager = LinearLayoutManager(this)
        rvDynamicList.adapter = usuarioAdapter

        // BOTÓN PACIENTES
        btnManagePatients.setOnClickListener {
            tvSectionTitle.text = "Pacientes Registrados"
            db.usuarioDao().getUsersByRol("PACIENTE").observe(this) { lista ->
                usuarioAdapter.updateData(lista)
            }
        }

        // BOTÓN MÉDICOS
        btnManageMedicos.setOnClickListener {
            tvSectionTitle.text = "Médicos Registrados"
            db.usuarioDao().getUsersByRol("MEDICO").observe(this) { lista ->
                usuarioAdapter.updateData(lista)
            }
        }

        btnLogout.setOnClickListener {
            val prefs = getSharedPreferences("session", MODE_PRIVATE)
            prefs.edit().clear().apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
