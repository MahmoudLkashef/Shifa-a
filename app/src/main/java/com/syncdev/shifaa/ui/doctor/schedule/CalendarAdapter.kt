import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.CalendarModel
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.CalendarListItemBinding

class CalendarAdapter : ListAdapter<CalendarModel, CalendarAdapter.CalendarViewHolder>(
    DiffCallback()
) {
    //To make the first item to be selected by default
    //referring to today
    private var selectedItemPosition = 0
    var onDateClicked: ((CalendarModel) ->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = CalendarListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }

    inner class CalendarViewHolder(private val binding: CalendarListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CalendarModel, position: Int) {
            binding.apply {
                tvDayOfMonth.text = item.dayOfMonth
                tvDayOfWeek.text = item.dayOfWeek

                if (position == selectedItemPosition) {
                    root.background = ContextCompat.getDrawable(root.context, R.drawable.selected_calendar_item_background)
                    tvDayOfMonth.setTextColor(Color.WHITE)
                    tvDayOfWeek.setTextColor(Color.WHITE)
                } else {
                    root.background = ContextCompat.getDrawable(root.context, R.drawable.default_calendar_item_background)
                    tvDayOfMonth.setTextColor(Color.BLACK)
                    tvDayOfWeek.setTextColor(Color.BLACK)
                }

                root.setOnClickListener {
                    val previousSelectedItemPosition = selectedItemPosition
                    selectedItemPosition = adapterPosition
                    notifyItemChanged(previousSelectedItemPosition)
                    notifyItemChanged(selectedItemPosition)

                    onDateClicked?.invoke(item)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CalendarModel>() {
        override fun areItemsTheSame(oldItem: CalendarModel, newItem: CalendarModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CalendarModel, newItem: CalendarModel): Boolean {
            return oldItem == newItem
        }
    }
}
