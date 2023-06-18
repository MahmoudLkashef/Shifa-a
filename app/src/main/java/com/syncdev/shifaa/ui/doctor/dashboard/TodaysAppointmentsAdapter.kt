package com.syncdev.shifaa.ui.doctor.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.Appointment
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.TodaysAppointmentsListItemBinding

class TodaysAppointmentsAdapter(private val context: Context) :
    ListAdapter<Appointment, TodaysAppointmentsAdapter.TodaysAppointmentsViewModel>(
        DiffCallback()
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodaysAppointmentsViewModel {
        val binding = TodaysAppointmentsListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TodaysAppointmentsViewModel(binding,context)
    }

    override fun onBindViewHolder(holder: TodaysAppointmentsViewModel, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class TodaysAppointmentsViewModel(
        private val binding: TodaysAppointmentsListItemBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Appointment) {
            binding.apply {
                tvPatientNameTodayAppintment.text =
                    "${item.patient.firstName} ${item.patient.lastName}"
                tvTimeTodayAppointment.text = item.time

                when (item.patient.gender) {
                    "Male" -> ContextCompat.getDrawable(context, R.drawable.patient_male)
                    "Female" -> ContextCompat.getDrawable(context, R.drawable.patient_female)
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