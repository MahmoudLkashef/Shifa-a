package com.syncdev.shifaa.ui.doctor.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentDoctorHomeBinding


class DoctorHomeFragment : Fragment() {
    private val TAG="DoctorHomeFragment"
    private lateinit var binding:FragmentDoctorHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_doctor_home,
            container,
            false
        )

        binding.tvTodayAppointmentsTitle.setOnClickListener {
            findNavController().navigate(DoctorHomeFragmentDirections.actionDoctorHomeFragmentToPatientDetailsFragment())
        }

        return binding.root
    }

}