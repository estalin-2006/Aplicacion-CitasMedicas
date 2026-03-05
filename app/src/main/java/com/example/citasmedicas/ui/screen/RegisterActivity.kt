package com.estalin.citasmedicasapp.ui.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel
    private val CODIGO_ADMIN_SECRETO = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val db = AppDatabase.getDatabase(this)
        val repository = AppRepository(
            db.usuarioDao(), 
            db.pacienteDao(), 
            db.medicoDao(), 
            db.citaDao(), 
            RetrofitInstance.api
        )
        viewModel = ViewModelProvider(this, RegisterViewModelFactory(repository))[RegisterViewModel::class.java]

        val name = findViewById<EditText>(R.id.etNombre)
        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val spinnerRol = findViewById<Spinner>(R.id.spinnerRol)
        val etEspecialidad = findViewById<EditText>(R.id.etEspecialidad)
        val register = findViewById<Button>(R.id.btnRegistrar)

        val roles = arrayOf("PACIENTE", "MEDICO", "ADMIN")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRol.adapter = adapter

        spinnerRol.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                etEspecialidad.visibility = if (roles[position] == "MEDICO") View.VISIBLE else View.GONE
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        register.setOnClickListener {
            val selectedRole = spinnerRol.selectedItem.toString()
            
            if (selectedRole == "ADMIN") {
                mostrarDialogoCodigoAdmin { codigoIngresado ->
                    if (codigoIngresado == CODIGO_ADMIN_SECRETO) {
                        ejecutarRegistro(name, email, password, selectedRole, null)
                    } else {
                        Toast.makeText(this, "Código de Administrador incorrecto", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                val especialidad = if (selectedRole == "MEDICO") etEspecialidad.text.toString() else null
                ejecutarRegistro(name, email, password, selectedRole, especialidad)
            }
        }
    }

    private fun ejecutarRegistro(name: EditText, email: EditText, pass: EditText, rol: String, especialidad: String?) {
        val nameText = name.text.toString().trim()
        val emailText = email.text.toString().trim()
        val passText = pass.text.toString().trim()

        if (nameText.isEmpty() || emailText.isEmpty() || passText.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val user = UsuarioEntity(
            nombre = nameText,
            email = emailText,
            password = passText,
            rol = rol,
            especialidad = especialidad
        )

        lifecycleScope.launch {
            try {
                Toast.makeText(this@RegisterActivity, "Registrando...", Toast.LENGTH_SHORT).show()
                val success = viewModel.register(user)
                if (success) {
                    Toast.makeText(this@RegisterActivity, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()
                    delay(500)
                    
                    // NAVEGAR AL LOGIN Y CERRAR EL REGISTRO
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish() 
                } else {
                    Toast.makeText(this@RegisterActivity, "El correo ya existe", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("REGISTRO_ERROR", "Error: ${e.message}")
                Toast.makeText(this@RegisterActivity, "Error de conexión", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun mostrarDialogoCodigoAdmin(onConfirm: (String) -> Unit) {
        val input = EditText(this)
        input.hint = "Introduce el código"
        android.app.AlertDialog.Builder(this)
            .setTitle("Verificación de Administrador")
            .setView(input)
            .setPositiveButton("Confirmar") { _, _ -> onConfirm(input.text.toString()) }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
