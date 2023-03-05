package com.syncdev.shifaa.ui.patient.appointments.upcoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.SchedulePatient
import com.syncdev.shifaa.databinding.UpcomingAppointmentsListItemBinding

class UpcomingAppointmentsAdapter :
    ListAdapter<SchedulePatient, UpcomingAppointmentsAdapter.UpcomingAppointmentsViewHolder>(
        DiffCallback()
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpcomingAppointmentsViewHolder {
        val binding = UpcomingAppointmentsListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UpcomingAppointmentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpcomingAppointmentsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class UpcomingAppointmentsViewHolder(private val binding: UpcomingAppointmentsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SchedulePatient) {
            binding.apply {
                tvDoctorNameUpcomingAppointments.text=item.name
                tvDateUpcomingAppointments.text=item.date
                tvUpcomingAppointmentsTime.text=item.time
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