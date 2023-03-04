package com.syncdev.shifaa.ui.doctor.patients.new_patients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.shifaa.databinding.NewPatientRequestListItemBinding
import com.syncdev.shifaa.ui.doctor.patients.DoctorPatientsFragmentDirections
import com.syncdev.shifaa.ui.doctor.patients.past_patients.PatientTest

class DoctorNewPatientAdapter (private val navController: NavController):
    ListAdapter<PatientTest, DoctorNewPatientAdapter.DoctorNewPatientViewHolder>(
        DiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorNewPatientViewHolder {
        val binding = NewPatientRequestListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DoctorNewPatientViewHolder(binding,navController)
    }

    override fun onBindViewHolder(holder: DoctorNewPatientViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DoctorNewPatientViewHolder(private val binding: NewPatientRequestListItemBinding,
    private val navController: NavController) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PatientTest) {
            binding.apply {
                tvNewPatientName.text = item.name
                tvDateNewPatient.text = item.date
                tvNewPatientTime.text = item.time
                imgNewPatientPic.setImageResource(item.profilePic)
                btnSeeMoreNewPatient.setOnClickListener {
                    navController.navigate(
                        DoctorPatientsFragmentDirections.actionDoctorPatientsFragmentToAppointmentRequestDetailsFragment(item.name)
                    )
                }
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