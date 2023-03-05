package com.syncdev.shifaa.ui.patient.appointments.canceled

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.SchedulePatient
import com.syncdev.shifaa.databinding.CanceledAppointmentsListItemBinding

class CanceledAppointmentsAdapter :
    ListAdapter<SchedulePatient, CanceledAppointmentsAdapter.CanceledAppointmentsViewHolder>(
        DiffCallback()
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CanceledAppointmentsViewHolder {
        val binding = CanceledAppointmentsListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CanceledAppointmentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CanceledAppointmentsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class CanceledAppointmentsViewHolder(private val binding: CanceledAppointmentsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SchedulePatient) {
            binding.apply {
                tvDoctorNameCanceledAppointments.text=item.name
                tvCanceledAppointmentsTime.text=item.time
                tvDateCanceledAppointments.text=item.date
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