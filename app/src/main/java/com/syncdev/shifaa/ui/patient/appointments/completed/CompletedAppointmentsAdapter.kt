package com.syncdev.shifaa.ui.patient.appointments.completed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.SchedulePatient
import com.syncdev.shifaa.databinding.CompletedAppointmentsListItemBinding

class CompletedAppointmentsAdapter :
    ListAdapter<SchedulePatient, CompletedAppointmentsAdapter.CompletedAppointmentsViewHolder>(
        DiffCallback()
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompletedAppointmentsViewHolder {
        val binding = CompletedAppointmentsListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CompletedAppointmentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompletedAppointmentsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class CompletedAppointmentsViewHolder(private val binding: CompletedAppointmentsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SchedulePatient) {
            binding.apply {
                tvDoctorNameCompletedAppointments.text=item.name
                tvDateCompletedAppointments.text=item.date
                tvCompletedAppointmentsTime.text=item.time
            }
        }
    }

}


class DiffCallback : DiffUtil.ItemCallback<SchedulePatient>() {
    override fun areItemsTheSame(oldItem: SchedulePatient, newItem: SchedulePatient): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SchedulePatient, newItem: SchedulePatient): Boolean {
        return oldItem == newItem
    }
}