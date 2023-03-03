package com.syncdev.shifaa.ui.doctor.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.SchedulePatient
import com.syncdev.shifaa.databinding.RescheduleListItemBinding

class RescheduleAdapter : ListAdapter<SchedulePatient, RescheduleAdapter.RescheduleViewHolder>(
    DiffCallback2()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RescheduleViewHolder {
        val binding =
            RescheduleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RescheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RescheduleViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class RescheduleViewHolder(private val binding: RescheduleListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SchedulePatient) {
            binding.apply {
                tvReschedulePatientName.text=item.name
                tvReschedulePatientStatus.text=item.status
                tvDateReschedule.text=item.date
                tvRescheduleTime.text=item.time
            }
        }
    }

}


class DiffCallback2 : DiffUtil.ItemCallback<SchedulePatient>() {
    override fun areItemsTheSame(oldItem: SchedulePatient, newItem: SchedulePatient): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SchedulePatient, newItem: SchedulePatient): Boolean {
        return oldItem == newItem
    }
}