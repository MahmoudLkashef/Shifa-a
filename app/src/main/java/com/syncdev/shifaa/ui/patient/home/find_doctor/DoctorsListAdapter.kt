package com.syncdev.shifaa.ui.patient.home.find_doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syncdev.domain.model.Doctor
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.DoctorsListItemBinding

class DoctorsListAdapter : ListAdapter<Doctor, DoctorsListAdapter.DoctorsListViewHolder>(
    DiffCallback()
), Filterable {

    var onItemClicked:((Doctor)->Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorsListViewHolder {
        val binding = DoctorsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorsListViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener{ onItemClicked?.invoke(item) }
        holder.bind(item)
    }

    class DoctorsListViewHolder(private val binding: DoctorsListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(doctor: Doctor) {
            binding.apply {
                tvDoctorNameFindDoctor.text = getFullName(doctor.firstName,doctor.lastName)
                tvDoctorSpecialityFindDoctor.text = doctor.speciality
                tvDoctorRate.text = if (doctor.numOfReviews == 0){
                     "Not Rated Yet"
                }else{
                    getRate(doctor.totalRating,doctor.numOfReviews)
                }
                if (doctor.gender == "Male"){
                    ivDoctorPicItem.setImageDrawable(ContextCompat.getDrawable(root.context,R.drawable.doctor_male))
                }else{
                    ivDoctorPicItem.setImageDrawable(ContextCompat.getDrawable(root.context,R.drawable.doctor_female))
                }
            }
        }

        private fun getFullName(fName: String, lName: String): String{
            return "$fName $lName"
        }

        private fun getRate(totalRating: Float, numOfReviews: Int): String {
            val rate = totalRating / numOfReviews
            return String.format("%.1f", rate)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchQuery = constraint?.toString()?.trim()
                val filteredList = if (searchQuery.isNullOrEmpty()) {
                    currentList // Show all items if the search query is empty
                } else {
                    currentList.filter { doctor ->
                        doctor.firstName.contains(searchQuery, ignoreCase = true) ||
                                doctor.lastName.contains(searchQuery, ignoreCase = true)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                submitList(results?.values as? List<Doctor> ?: emptyList())
            }
        }
    }


}


class DiffCallback : DiffUtil.ItemCallback<Doctor>() {
    override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
        return oldItem == newItem
    }
}