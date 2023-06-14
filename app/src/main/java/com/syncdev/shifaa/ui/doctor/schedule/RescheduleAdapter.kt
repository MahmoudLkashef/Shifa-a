package com.syncdev.shifaa.ui.doctor.schedule

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.Appointment
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.RescheduleListItemBinding

class RescheduleAdapter(private val context: Context) : ListAdapter<Appointment, RescheduleAdapter.RescheduleViewHolder>(
    DiffCallback2()
) {

    var onCancelClicked:((Appointment)->Unit)? = null
    var onSeeMoreClicked:((Appointment)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RescheduleViewHolder {
        val binding =
            RescheduleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RescheduleViewHolder(binding,context,onCancelClicked, onSeeMoreClicked)
    }

    override fun onBindViewHolder(holder: RescheduleViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class RescheduleViewHolder(
        private val binding: RescheduleListItemBinding,
        private val context: Context,
        private val onCancelClicked: ((Appointment)->Unit)?,
        private val onRescheduleClicked: ((Appointment)->Unit)?,
        ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(appointment: Appointment) {
            binding.apply {
                val fullName = "${appointment.patient.firstName} " + appointment.patient.lastName
                tvReschedulePatientName.text= fullName
                tvReschedulePatientStatus.text= appointment.state
                tvDateReschedule.text= appointment.date
                tvRescheduleTime.text= appointment.time
                when(appointment.patient.gender){
                    "Male" -> {
                        imgReschedulePatientPic.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.patient_male
                            )
                        )
                    }
                    "Female" -> {
                        imgReschedulePatientPic.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.patient_female
                            )
                        )
                    }
                }

                btnCancelRescheduleDoctor.setOnClickListener {
                    onCancelClicked?.invoke(appointment)
                }

                btnSeeMoreAppointmentDoctor.setOnClickListener {
                    onRescheduleClicked?.invoke(appointment)
                }
            }
        }
    }

}


class DiffCallback2 : DiffUtil.ItemCallback<Appointment>() {
    override fun areItemsTheSame(oldItem: Appointment, newItem: Appointment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Appointment, newItem: Appointment): Boolean {
        return oldItem == newItem
    }
}