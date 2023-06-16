package com.syncdev.shifaa.ui.doctor.home.prescription

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.Medication
import com.syncdev.shifaa.databinding.PrescriptionListItemBinding

class PrescriptionAdapter : ListAdapter<Medication, PrescriptionAdapter.PrescriptionViewHolder>(
    DiffCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrescriptionViewHolder {
        val binding =
            PrescriptionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PrescriptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PrescriptionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class PrescriptionViewHolder(private val binding: PrescriptionListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Medication) {
            binding.apply {
                tvSchedulePrescription.text=item.scheduleLabels.joinToString(" - ")
                tvDosePrescription.text="Dose: ${item.dosage} Unit"
                tvDurationPrescription.text="Duration: ${item.period} Days"
                tvFrequencyPrescription.text="Frequency: ${item.frequency}"
                tvMedicineNamePrescription.text=item.name
            }
        }
    }

}


class DiffCallback : DiffUtil.ItemCallback<Medication>() {
    override fun areItemsTheSame(oldItem: Medication, newItem: Medication): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Medication, newItem: Medication): Boolean {
        return oldItem == newItem
    }
}