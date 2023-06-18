package com.syncdev.shifaa.ui.patient.medicine

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.ScheduledMedication
import com.syncdev.shifaa.databinding.MedicationListItemBinding
import com.syncdev.shifaa.utils.ImageMapping

class MedicineAdapter(private val context: Context) :
    ListAdapter<ScheduledMedication, MedicineAdapter.MedicineViewHolder>(
        DiffCallback()
    ) {

    var onMedicineClicked:((Int)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding =
            MedicationListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicineViewHolder(binding,context, onMedicineClicked)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MedicineViewHolder(
        private val binding: MedicationListItemBinding,
        private val context: Context,
        private val onMedicineClicked:((Int)->Unit)?
        ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(medicine: ScheduledMedication) {
            binding.apply {
                tvMedicationNameCv.text = medicine.name
                tvMedicationTypeCv.text = medicine.type
                tvMedicationTimeCv.text = medicine.time
                val drawable = ImageMapping(context).getDrawableByType(medicine.type)
                ivMedication.setImageDrawable(drawable)

                cvMedication.setOnClickListener {
                    onMedicineClicked?.invoke(medicine.id)
                }
            }
        }
    }

}


class DiffCallback : DiffUtil.ItemCallback<ScheduledMedication>() {
    override fun areItemsTheSame(
        oldItem: ScheduledMedication,
        newItem: ScheduledMedication
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ScheduledMedication,
        newItem: ScheduledMedication
    ): Boolean {
        return oldItem == newItem
    }
}