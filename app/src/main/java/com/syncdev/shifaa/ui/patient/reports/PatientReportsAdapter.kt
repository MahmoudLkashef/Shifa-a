package com.syncdev.shifaa.ui.patient.reports

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.Appointment
import com.syncdev.domain.model.Medication
import com.syncdev.shifaa.databinding.PrescriptionReportListItemBinding

class PatientReportsAdapter :
    ListAdapter<Appointment, PatientReportsAdapter.PatientReportsViewHolder>(
        DiffCallback()
    ) {

    var onDispenseMedicineClicked:((List<Medication>)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientReportsViewHolder {
        val binding = PrescriptionReportListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PatientReportsViewHolder(binding,onDispenseMedicineClicked)
    }

    override fun onBindViewHolder(holder: PatientReportsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class PatientReportsViewHolder(
        private val binding: PrescriptionReportListItemBinding,
        private val onDispenseMedicineClicked:((List<Medication>)->Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(appointment: Appointment) {
            binding.apply {
                val fullName = "${appointment.doctor.firstName} " + appointment.doctor.lastName
                tvDoctorNamePrescriptionReport.text = fullName
                tvDoctorSpecialtyPrescriptionReport.text = appointment.doctor.speciality
                tvDoctorEmailReport.text = appointment.doctor.email
                tvDoctorPhoneReport.text = appointment.doctor.phoneNumber
                tvDatePrescriptionReport.text = appointment.date
                tvTimePrescriptionReport.text = appointment.time
                tvDoctorPrescreiptionAdvice.text = appointment.prescription?.advice

                btnDispenseMedicine.setOnClickListener {
                    appointment.prescription?.medicines?.let { medications ->
                        onDispenseMedicineClicked?.invoke(medications)
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