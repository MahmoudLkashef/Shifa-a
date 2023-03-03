package com.syncdev.shifaa.ui.doctor.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.Calendar
import com.syncdev.shifaa.databinding.CalendarListItemBinding

class CalendarAdapter : ListAdapter<Calendar, CalendarAdapter.CalendarViewHolder>(
    DiffCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding =
            CalendarListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class CalendarViewHolder(private val binding: CalendarListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Calendar) {
            binding.apply {
                tvDayOfMonth.text=item.dayOfMonth
                tvDayOfWeek.text=item.dayOfWeek
            }
        }
    }

}


class DiffCallback : DiffUtil.ItemCallback<Calendar>() {
    override fun areItemsTheSame(oldItem: Calendar, newItem: Calendar): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Calendar, newItem: Calendar): Boolean {
        return oldItem == newItem
    }
}