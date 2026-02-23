package com.estalin.citasmedicasapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.estalin.citasmedicasapp.R
import com.estalin.citasmedicasapp.data.entity.CitaEntity

class CitaAdapter(private val citas: List<CitaEntity>) :
    RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {

    class CitaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fecha: TextView = view.findViewById(R.id.tvFechaCita)
        val hora: TextView = view.findViewById(R.id.tvHoraCita)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citas[position]
        holder.fecha.text = cita.fecha
        holder.hora.text = cita.hora
    }

    override fun getItemCount() = citas.size
}
