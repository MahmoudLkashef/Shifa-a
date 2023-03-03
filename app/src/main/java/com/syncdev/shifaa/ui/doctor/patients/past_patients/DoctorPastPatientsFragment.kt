package com.syncdev.shifaa.ui.doctor.patients.past_patients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentDoctorPastPatientsBinding


class DoctorPastPatientsFragment : Fragment() {

    private lateinit var binding: FragmentDoctorPastPatientsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDoctorPastPatientsBinding.inflate(inflater,container,false)


        return binding.root
    }


}