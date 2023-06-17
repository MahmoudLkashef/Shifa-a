package com.syncdev.shifaa_scanner.pharmacist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.shifaa_scanner.databinding.PrescriptionListItemBinding
import com.syncdev.shifaa_scanner.model.Medication

class MedicationAdapter : ListAdapter<Medication, MedicationAdapter.MedicationViewHolder>(
    DiffCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationViewHolder {
        val binding =
            PrescriptionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicationViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MedicationViewHolder(private val binding: PrescriptionListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Medication) {
            binding.apply {
                tvSchedulePrescription.text=item.scheduleLabels.joinToString(" - ")
                tvDosePrescription.text="Dose: ${item.dosage} Unit"
                tvDurationPrescription.text="Duration: ${item.period} Days"
                tvFrequencyPrescription.text="Frequency: ${item.frequency}"
                tvMedicineTypePrescription.text="Type: ${item.type}"
                tvMedicineNamePrescription.text=item.name
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