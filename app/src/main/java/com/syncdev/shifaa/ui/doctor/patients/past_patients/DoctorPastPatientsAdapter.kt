package com.syncdev.shifaa.ui.doctor.patients.past_patients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.shifaa.databinding.PastPatientListItemBinding

class DoctorPastPatientsAdapter :
    ListAdapter<PatientTest, DoctorPastPatientsAdapter.DoctorPastPatientsViewHolder>(
        DiffCallback()
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DoctorPastPatientsViewHolder {
        val binding =
        PastPatientListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorPastPatientsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorPastPatientsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DoctorPastPatientsViewHolder(private val binding: PastPatientListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PatientTest) {
            binding.apply {
                tvPastPatientName.text = item.name
                tvPastPatientApptDate.text = item.date
                ivPastPatient.setImageResource(item.profilePic)
            }
        }
    }

}


class DiffCallback : DiffUtil.ItemCallback<PatientTest>() {
    override fun areItemsTheSame(oldItem: PatientTest, newItem: PatientTest): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PatientTest, newItem: PatientTest): Boolean {
        return oldItem == newItem
    }
}