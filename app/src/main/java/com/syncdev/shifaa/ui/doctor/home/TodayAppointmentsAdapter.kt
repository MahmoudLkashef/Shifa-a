package com.syncdev.shifaa.ui.doctor.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.Appointment
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.TodaysAppointmentsListItemBinding

class TodayAppointmentsAdapter(private val context: Context) :
    ListAdapter<Appointment, TodayAppointmentsAdapter.TodayAppointmentsViewHolder>(
        DiffCallback()
    ) {

    var onAppointmentClicked:((Appointment)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayAppointmentsViewHolder {
        val binding = TodaysAppointmentsListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TodayAppointmentsViewHolder(binding,context,onAppointmentClicked)
    }

    override fun onBindViewHolder(holder: TodayAppointmentsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class TodayAppointmentsViewHolder(
        private val binding: TodaysAppointmentsListItemBinding,
        private val context: Context,
        private val onAppointmentClicked: ((Appointment)->Unit)?
        ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(appointment: Appointment) {
            binding.apply {
                val fullName = "${appointment.patient.firstName} " + appointment.patient.lastName
                tvPatientNameTodayAppintment.text = fullName
                tvTimeTodayAppointment.text = appointment.time
                when(appointment.patient.gender){
                    "Male" -> ContextCompat.getDrawable(context, R.drawable.patient_male)
                    "Female" -> ContextCompat.getDrawable(context, R.drawable.patient_female)
                }
                cvTodayAppointments.setOnClickListener {
                    onAppointmentClicked?.invoke(appointment)
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