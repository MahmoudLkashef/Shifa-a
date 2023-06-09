package com.syncdev.shifaa.ui.patient.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentPatientHomeBinding


class PatientHomeFragment : Fragment() {

    private lateinit var binding: FragmentPatientHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_patient_home,
            container,
            false
        )

        binding.btnBook.setOnClickListener {
            findNavController().navigate(PatientHomeFragmentDirections.actionPatientHomeFragmentToFindDoctorFragment())
        }

        return binding.root
    }


}