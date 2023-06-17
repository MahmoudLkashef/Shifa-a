package com.syncdev.shifaa.ui.doctor.home.prescription.patient_medical_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.Medication
import com.syncdev.shifaa.databinding.PatientMedicationsPatientDetailsListItemBinding

class PatientMedicationAdapter :
    ListAdapter<Medication, PatientMedicationAdapter.PatientMedicationViewModel>(
        DiffCallback()
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientMedicationViewModel {
        val binding = PatientMedicationsPatientDetailsListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PatientMedicationViewModel(binding)
    }

    override fun onBindViewHolder(holder: PatientMedicationViewModel, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class PatientMedicationViewModel(private val binding: PatientMedicationsPatientDetailsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Medication) {
            binding.apply {
                tvMedicineNamePatientDetails.text=item.name
                tvMedicineDiseasesPatientDetails.text=item.dosage
            }
        }
    }

}


class DiffCallback : DiffUtil.ItemCallback<Medication>() {
    override fun areItemsTheSame(oldItem: Medication, newItem: Medication): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Medication, newItem: Medication): Boolean {
        return oldItem == newItem
    }
}