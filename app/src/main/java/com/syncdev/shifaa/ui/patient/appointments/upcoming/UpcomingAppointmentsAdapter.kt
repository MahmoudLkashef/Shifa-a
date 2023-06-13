package com.syncdev.shifaa.ui.patient.appointments.upcoming

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.Appointment
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.UpcomingAppointmentsListItemBinding

class UpcomingAppointmentsAdapter(private val context: Context) :
    ListAdapter<Appointment, UpcomingAppointmentsAdapter.UpcomingAppointmentsViewHolder>(
        DiffCallback()
    ) {

    var onCancelClicked:((Appointment)->Unit)? = null
    var onRescheduleClicked:((Appointment)->Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpcomingAppointmentsViewHolder {
        val binding = UpcomingAppointmentsListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UpcomingAppointmentsViewHolder(binding,context, onCancelClicked, onRescheduleClicked)
    }

    override fun onBindViewHolder(holder: UpcomingAppointmentsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class UpcomingAppointmentsViewHolder(
        private val binding: UpcomingAppointmentsListItemBinding,
        private val context: Context,
        private val onCancelClicked:((Appointment)->Unit)?,
        private val onRescheduleClicked:((Appointment)->Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(appointment: Appointment) {
            val fullName = "${appointment.doctor.firstName} " + appointment.doctor.lastName
            binding.apply {
                tvDoctorNameUpcomingAppointments.text= fullName
                tvDateUpcomingAppointments.text= appointment.date
                tvUpcomingAppointmentsTime.text= appointment.time

                when(appointment.doctor.gender){
                    "Male" -> {
                        imgDoctorUpcomingAppointments.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.doctor_male
                            )
                        )
                    }
                    "Female" -> {
                        imgDoctorUpcomingAppointments.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.doctor_female
                            )
                        )
                    }
                }

                btnCancelUpcomingAppointments.setOnClickListener {
                    onCancelClicked?.invoke(appointment)
                }
                btnReschedule.setOnClickListener {
                    onRescheduleClicked?.invoke(appointment)
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