package com.syncdev.shifaa.ui.patient.appointments.completed

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.Appointment
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.CompletedAppointmentsListItemBinding

class CompletedAppointmentsAdapter(private val context: Context) :
    ListAdapter<Appointment, CompletedAppointmentsAdapter.CompletedAppointmentsViewHolder>(
        DiffCallback()
    ) {

    var onBookAgainClicked:((Appointment)->Unit)? = null
    var onLeaveAReviewClicked:((Appointment)->Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompletedAppointmentsViewHolder {
        val binding = CompletedAppointmentsListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CompletedAppointmentsViewHolder(binding,context, onBookAgainClicked, onLeaveAReviewClicked)
    }

    override fun onBindViewHolder(holder: CompletedAppointmentsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class CompletedAppointmentsViewHolder(
        private val binding: CompletedAppointmentsListItemBinding,
        private val context: Context,
        private val onBookAgainClicked:((Appointment)->Unit)?,
        private val onLeaveAReviewClicked:((Appointment)->Unit)?
        ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(appointment: Appointment) {
            binding.apply {
                val fullName = "${appointment.doctor.firstName} " + appointment.doctor.lastName
                tvDoctorNameCompletedAppointments.text= fullName
                tvDateCompletedAppointments.text= appointment.date
                tvCompletedAppointmentsTime.text = appointment.time

                when(appointment.doctor.gender){
                    "Male" -> {
                        imgDoctorCompletedAppointments.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.doctor_male
                            )
                        )
                    }
                    "Female" -> {
                        imgDoctorCompletedAppointments.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.doctor_female
                            )
                        )
                    }
                }

                btnBookAgainCompletedAppointments.setOnClickListener {
                    onBookAgainClicked?.invoke(appointment)
                }
                btnLeaveAReview.setOnClickListener {
                    onLeaveAReviewClicked?.invoke(appointment)
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