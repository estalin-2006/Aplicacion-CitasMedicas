package com.estalin.citasmedicasapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.estalin.citasmedicasapp.R
import com.estalin.citasmedicasapp.data.entity.PacienteEntity

class PacienteAdapter(
    private var pacientes: List<PacienteEntity>,
    private val onEditClick: (PacienteEntity) -> Unit
) : RecyclerView.Adapter<PacienteAdapter.PacienteViewHolder>() {

    class PacienteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvTelefono: TextView = view.findViewById(R.id.tvTelefono)
        val btnEdit: View = view.findViewById(R.id.btnEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacienteViewHolder {
        // CORRECCIÓN: Se debe usar R.layout para inflar una vista, no R.id
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_paciente, parent, false)
        return PacienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: PacienteViewHolder, position: Int) {
        val paciente = pacientes[position]
        holder.tvNombre.text = paciente.nombre
        holder.tvTelefono.text = "Teléfono: ${paciente.telefono}"
        holder.btnEdit.setOnClickListener { onEditClick(paciente) }
    }

    override fun getItemCount() = pacientes.size

    fun updateData(newPacientes: List<PacienteEntity>) {
        pacientes = newPacientes
        notifyDataSetChanged()
    }
}
