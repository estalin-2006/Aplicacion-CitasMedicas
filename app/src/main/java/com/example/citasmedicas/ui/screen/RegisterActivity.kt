package com.estalin.citasmedicasapp.ui.screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.estalin.citasmedicasapp.R
import com.estalin.citasmedicasapp.data.AppDatabase
import com.estalin.citasmedicasapp.data.AppRepository
import com.estalin.citasmedicasapp.data.RetrofitInstance
import com.estalin.citasmedicasapp.data.entity.UsuarioEntity
import com.estalin.citasmedicasapp.ui.viewmodel.RegisterViewModel
import com.estalin.citasmedicasapp.ui.viewmodel.RegisterViewModelFactory
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val db = AppDatabase.getDatabase(this)
        val repo = AppRepository(db.usuarioDao(), db.pacienteDao(), RetrofitInstance.api)
        viewModel = ViewModelProvider(this, RegisterViewModelFactory(repo))[RegisterViewModel::class.java]

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val spRol = findViewById<Spinner>(R.id.spinnerRol)
        val etEspecialidad = findViewById<EditText>(R.id.etEspecialidad)
        val btnRegister = findViewById<Button>(R.id.btnRegistrar)

        val roles = arrayOf("PACIENTE", "MEDICO", "ADMIN")
        spRol.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, roles)

        // Lógica dinámica para mostrar/ocultar Especialidad
        spRol.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (roles[position] == "MEDICO") {
                    etEspecialidad.visibility = View.VISIBLE
                } else {
                    etEspecialidad.visibility = View.GONE
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        btnRegister.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val rol = spRol.selectedItem.toString()
            val especialidad = if (rol == "MEDICO") etEspecialidad.text.toString().trim() else null

            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || (rol == "MEDICO" && especialidad.isNullOrEmpty())) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val usuario = UsuarioEntity(nombre = nombre, email = email, password = password, rol = rol, especialidad = especialidad)
                val exito = viewModel.register(usuario)

                if (exito) {
                    Toast.makeText(this@RegisterActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "El correo ya existe", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
