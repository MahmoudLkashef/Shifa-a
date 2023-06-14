package com.syncdev.shifaa.ui.patient.home.upcoming_appointments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.Appointment
import com.syncdev.shifaa.databinding.UpcomingAppointmentsPatientHomeListItemBinding

class UpcomingAppointmentsHomeAdapter :
    ListAdapter<Appointment, UpcomingAppointmentsHomeAdapter.UpcomingAppointmentsHomeViewHolder>(
        DiffCallback()
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpcomingAppointmentsHomeViewHolder {
        val binding = UpcomingAppointmentsPatientHomeListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UpcomingAppointmentsHomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpcomingAppointmentsHomeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class UpcomingAppointmentsHomeViewHolder(private val binding: UpcomingAppointmentsPatientHomeListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Appointment) {
            binding.apply {
                val doctorFullName = "Dr.${item.doctor.firstName} " + item.doctor.lastName
                tvDoctorNameUpcomingAppointmentsHome.text=doctorFullName
                tvDoctorSpecialtyUpcomingAppointmentsHome.text=item.doctor.speciality
                tvDateUpcomingAppointmentsHome.text=item.date
                tvTimeUpcomingAppointmentsHome.text=item.time
            }
        }
    }

}


class DiffCallback : DiffUtil.ItemCallback<Appointment>() {
    override fun areItemsTheSame(
        oldItem: Appointment,
        newItem: Appointment
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Appointment,
        newItem: Appointment
    ): Boolean {
        return oldItem == newItem
    }
}