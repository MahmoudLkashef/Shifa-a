package com.syncdev.shifaa.ui.patient.home.find_doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentFindDoctorBinding

class FindDoctorFragment : Fragment() {

    private lateinit var binding: FragmentFindDoctorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_find_doctor,
            container,
            false
        )

        val specialty:MutableList<String> = resources.getStringArray(R.array.specialty).toMutableList()
        specialty.sort()
        specialty.add(0, "All Specialities")
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, specialty)

        binding.apply {
            dropdownMenuFilterSpecialty.setAdapter(arrayAdapter)
            dropdownMenuFilterSpecialty.setText(specialty[0], false)
            btnBackFindDoctor.setOnClickListener {
                findNavController().popBackStack()
            }
        }


        return binding.root
    }

}