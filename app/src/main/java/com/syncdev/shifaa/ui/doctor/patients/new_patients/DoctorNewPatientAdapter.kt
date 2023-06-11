package com.syncdev.shifaa.ui.doctor.patients.new_patients

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.AppointmentRequest
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.NewPatientRequestListItemBinding

class DoctorNewPatientAdapter(private val context: Context):
    ListAdapter<AppointmentRequest, DoctorNewPatientAdapter.DoctorNewPatientViewHolder>(
        DiffCallback()
    ) {

    var onSeeMoreClicked:((AppointmentRequest)->Unit)? = null
    var onDeclineClicked:((AppointmentRequest)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorNewPatientViewHolder {
        val binding = NewPatientRequestListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DoctorNewPatientViewHolder(context,binding,onSeeMoreClicked,onDeclineClicked)
    }

    override fun onBindViewHolder(holder: DoctorNewPatientViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DoctorNewPatientViewHolder(
        private val context: Context,
        private val binding: NewPatientRequestListItemBinding,
        private val onSeeMoreClicked:((AppointmentRequest)->Unit)?,
        private val onDeclineClicked:((AppointmentRequest)->Unit)?) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(appointmentRequest: AppointmentRequest) {
            val fullName = "${appointmentRequest.patient.firstName} " + appointmentRequest.patient.lastName
            binding.apply {
                tvNewPatientName.text = fullName
                tvDateNewPatient.text = appointmentRequest.date
                tvNewPatientTime.text = appointmentRequest.time
                when(appointmentRequest.patient.gender){
                    "Male" -> {
                        imgNewPatientPic.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.patient_male
                            )
                        )
                    }
                    "Female" -> {
                        imgNewPatientPic.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.patient_female
                            )
                        )
                    }
                }
                btnSeeMoreNewPatient.setOnClickListener {
                    onSeeMoreClicked?.invoke(appointmentRequest)
                }
                btnCancelRequestNewPatient.setOnClickListener {
                    onDeclineClicked?.invoke(appointmentRequest)
                }
            }
        }
    }

}


class DiffCallback : DiffUtil.ItemCallback<AppointmentRequest>() {
    override fun areItemsTheSame(oldItem: AppointmentRequest, newItem: AppointmentRequest): Boolean {
        return oldItem.patient.id == newItem.patient.id
    }

    override fun areContentsTheSame(oldItem: AppointmentRequest, newItem: AppointmentRequest): Boolean {
        return oldItem == newItem
    }
}