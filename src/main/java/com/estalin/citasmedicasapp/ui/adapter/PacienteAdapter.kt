package com.estalin.citasmedicasapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.estalin.citasmedicasapp.data.entity.PacienteEntity
import com.estalin.citasmedicasapp.databinding.ItemPacienteBinding

class PacienteAdapter :
    ListAdapter<PacienteEntity, PacienteAdapter.ViewHolder>(DIFF) {

    class ViewHolder(val binding: ItemPacienteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPacienteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paciente = getItem(position)
        holder.binding.tvNombre.text = paciente.nombre
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<PacienteEntity>() {
            override fun areItemsTheSame(
                oldItem: PacienteEntity,
                newItem: PacienteEntity
            ) = oldItem.pacienteId == newItem.pacienteId

            override fun areContentsTheSame(
                oldItem: PacienteEntity,
                newItem: PacienteEntity
            ) = oldItem == newItem
        }
    }
}