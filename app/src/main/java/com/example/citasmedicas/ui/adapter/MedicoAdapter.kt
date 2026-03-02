package com.estalin.citasmedicasapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.estalin.citasmedicasapp.R
import com.estalin.citasmedicasapp.data.entity.UsuarioEntity

class MedicoAdapter(
    private var medicos: List<UsuarioEntity>,
    private val onEditClick: (UsuarioEntity) -> Unit
) : RecyclerView.Adapter<MedicoAdapter.MedicoViewHolder>() {

    class MedicoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvEspecialidad: TextView = view.findViewById(R.id.tvEspecialidad)
        val btnEdit: View = view.findViewById(R.id.btnEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_medico, parent, false)
        return MedicoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicoViewHolder, position: Int) {
        val medico = medicos[position]
        holder.tvNombre.text = "Dr. ${medico.nombre}"
        holder.tvEspecialidad.text = "Especialidad: ${medico.especialidad ?: "Médico General"}"
        holder.btnEdit.setOnClickListener { onEditClick(medico) }
    }

    override fun getItemCount() = medicos.size

    fun updateData(newMedicos: List<UsuarioEntity>) {
        medicos = newMedicos
        notifyDataSetChanged()
    }
}
