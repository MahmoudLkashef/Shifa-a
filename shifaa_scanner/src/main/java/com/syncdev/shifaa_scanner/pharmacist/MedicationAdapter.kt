package com.syncdev.shifaa_scanner.pharmacist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.shifaa_scanner.databinding.PrescriptionListItemBinding
import com.syncdev.shifaa_scanner.model.Medication
import com.syncdev.shifaa_scanner.utils.ImageMapping

class MedicationAdapter(private val context: Context) : ListAdapter<Medication, MedicationAdapter.MedicationViewHolder>(
    DiffCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationViewHolder {
        val binding =
            PrescriptionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicationViewHolder(binding,context)
    }

    override fun onBindViewHolder(holder: MedicationViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MedicationViewHolder(
        private val binding: PrescriptionListItemBinding,
        private val context: Context
        ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(medicine: Medication) {
            binding.apply {
                val duration = if (medicine.period == "1") "1 day" else "${medicine.period} days"
                val frequency = if (medicine.frequency == "1") "Once a day for " else "${medicine.frequency} times a day for "
                val drawable = ImageMapping(context).getDrawableByType(medicine.type)
                ivMedication.setImageDrawable(drawable)
                tvMedicationNameCv.text = medicine.name
                tvDosage.text = "${medicine.dosage} mg"
                tvMedicationTypeCv.text = medicine.type
                tvDurationListItem.text = frequency + duration
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