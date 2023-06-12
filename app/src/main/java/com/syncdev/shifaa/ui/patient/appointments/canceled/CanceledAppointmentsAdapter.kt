package com.syncdev.shifaa.ui.patient.appointments.canceled

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.Appointment
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.CanceledAppointmentsListItemBinding

class CanceledAppointmentsAdapter(private val context: Context):
    ListAdapter<Appointment, CanceledAppointmentsAdapter.CanceledAppointmentsViewHolder>(
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
        return CanceledAppointmentsViewHolder(binding,context)
    }

    override fun onBindViewHolder(holder: CanceledAppointmentsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class CanceledAppointmentsViewHolder(
        private val binding: CanceledAppointmentsListItemBinding,
        private val context: Context
        ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(appointment: Appointment) {
            binding.apply {
                val fullName = "${appointment.doctor.firstName} " + appointment.doctor.lastName
                tvDoctorNameCanceledAppointments.text = fullName
                tvCanceledAppointmentsTime.text = appointment.time
                tvDateCanceledAppointments.text = appointment.date

                when(appointment.doctor.gender){
                    "Male" -> {
                        imgDoctorCanceledAppointments.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.doctor_male
                            )
                        )
                    }
                    "Female" -> {
                        imgDoctorCanceledAppointments.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.doctor_female
                            )
                        )
                    }
                }
            }
        }
    }

}


class DiffCallback : DiffUtil.ItemCallback<Appointment>() {
    override fun areItemsTheSame(oldItem: Appointment, newItem: Appointment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Appointment, newItem: Appointment): Boolean {
        return oldItem == newItem
    }
}