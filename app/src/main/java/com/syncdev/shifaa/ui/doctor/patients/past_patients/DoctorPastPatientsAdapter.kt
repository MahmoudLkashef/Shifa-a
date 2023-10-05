package com.syncdev.shifaa.ui.doctor.patients.past_patients

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.Appointment
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.PastPatientListItemBinding

class DoctorPastPatientsAdapter(private val context: Context) :
    ListAdapter<Appointment, DoctorPastPatientsAdapter.DoctorPastPatientsViewHolder>(
        DiffCallback()
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DoctorPastPatientsViewHolder {
        val binding =
        PastPatientListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorPastPatientsViewHolder(binding,context)
    }

    override fun onBindViewHolder(holder: DoctorPastPatientsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DoctorPastPatientsViewHolder(
        private val binding: PastPatientListItemBinding,
        private val context: Context
        ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(appointment: Appointment) {
            val fullName = "${appointment.patient.firstName} " + appointment.patient.lastName
            binding.apply {
                tvPastPatientName.text = fullName
                tvPastPatientApptDate.text = appointment.date
                when(appointment.patient.gender){
                    "Male" -> ivPastPatient.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.patient_male)
                    )
                    "Female" -> ivPastPatient.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.patient_female)
                    )
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