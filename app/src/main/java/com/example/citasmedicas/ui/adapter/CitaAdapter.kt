package com.estalin.citasmedicasapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.estalin.citasmedicasapp.R
import com.estalin.citasmedicasapp.data.entity.CitaConDoctor

class CitaAdapter(
    private var citas: List<CitaConDoctor>, // Ahora recibe CitaConDoctor
    private val showStatusSelector: Boolean = false,
    private val onStatusChange: (CitaConDoctor, String) -> Unit
) : RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {

    private val estados = arrayOf("PENDIENTE", "ATENDIDA", "CANCELADA")

    class CitaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFecha: TextView = view.findViewById(R.id.tvCitaFecha)
        val tvHora: TextView = view.findViewById(R.id.tvCitaHora)
        val tvInfoExtra: TextView = view.findViewById(R.id.tvPacienteId) // Reusamos para Especialidad
        val containerEstado: View = view.findViewById(R.id.containerEstado)
        val spEstado: Spinner = view.findViewById(R.id.spEstadoCita)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val item = citas[position]
        val cita = item.cita
        val medico = item.medico

        holder.tvFecha.text = "Fecha: ${cita.fecha}"
        holder.tvHora.text = "Hora: ${cita.hora}"
        
        // MOSTRAR ESPECIALIDAD EN LUGAR DE ID
        holder.tvInfoExtra.text = "Dr. ${medico.nombre}\n${medico.especialidad ?: "Gral"}"

        if (showStatusSelector) {
            holder.containerEstado.visibility = View.VISIBLE
            val adapter = ArrayAdapter(holder.itemView.context, android.R.layout.simple_spinner_item, estados)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            holder.spEstado.adapter = adapter

            val initialPosition = estados.indexOf(cita.estado)
            if (initialPosition >= 0) holder.spEstado.setSelection(initialPosition)

            holder.spEstado.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    val nuevoEstado = estados[pos]
                    if (nuevoEstado != cita.estado) {
                        onStatusChange(item, nuevoEstado)
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        } else {
            holder.containerEstado.visibility = View.GONE
        }
    }

    override fun getItemCount() = citas.size

    fun updateData(newList: List<CitaConDoctor>) {
        citas = newList
        notifyDataSetChanged()
    }
}
