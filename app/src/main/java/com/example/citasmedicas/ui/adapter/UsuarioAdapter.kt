package com.estalin.citasmedicasapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.estalin.citasmedicasapp.R
import com.estalin.citasmedicasapp.data.entity.UsuarioEntity

class UsuarioAdapter(
    private var usuarios: List<UsuarioEntity>,
    private val onEditClick: (UsuarioEntity) -> Unit // Añadimos el callback de edición
) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    class UsuarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvSubtitulo: TextView = view.findViewById(R.id.tvTelefono)
        val btnEdit: ImageButton = view.findViewById(R.id.btnEdit) // Referencia al lápiz
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_paciente, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = usuarios[position]
        holder.tvNombre.text = usuario.nombre
        holder.tvSubtitulo.text = if (usuario.rol == "MEDICO") 
            "Especialidad: ${usuario.especialidad}" else "Email: ${usuario.email}"
        
        // Al presionar el lápiz, disparamos la acción de editar
        holder.btnEdit.setOnClickListener { onEditClick(usuario) }
    }

    override fun getItemCount() = usuarios.size

    fun updateData(newUsuarios: List<UsuarioEntity>) {
        usuarios = newUsuarios
        notifyDataSetChanged()
    }
}
