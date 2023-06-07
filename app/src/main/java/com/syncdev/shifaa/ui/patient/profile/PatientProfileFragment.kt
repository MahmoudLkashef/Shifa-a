package com.syncdev.shifaa.ui.patient.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentPatientProfileBinding
import com.syncdev.shifaa.utils.SharedPreferencesUtils


class PatientProfileFragment : Fragment() {

    private val TAG = "PatientProfileFragment"
    private lateinit var binding:FragmentPatientProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_patient_profile,
            container,
            false
        )

        binding.cvEditProfilePatient.setOnClickListener {
            findNavController().navigate(PatientProfileFragmentDirections.actionPatientProfileFragmentToPatientEditProfileFragment())
        }

        val patient = SharedPreferencesUtils().getPatientFromSharedPreferences(requireContext())
        val name = "${patient?.firstName} ${patient?.lastName}"
        binding.tvPatientNameProfile.text = name

        return binding.root
    }


}