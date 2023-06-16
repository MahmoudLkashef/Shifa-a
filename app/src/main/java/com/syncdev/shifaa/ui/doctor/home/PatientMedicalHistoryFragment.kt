package com.syncdev.shifaa.ui.doctor.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentPatientMedicalHistoryBinding

class PatientMedicalHistoryFragment : Fragment() {
    private val TAG = "PatientMedicalHistoryFragment"
    private lateinit var binding: FragmentPatientMedicalHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_patient_medical_history,
            container,
            false
        )

        binding.apply {
            btnBackPatientMedicalHistory.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        val chronicDiseases = mutableListOf<String>("Hypertension")

        displayChronicDiseasesList(chronicDiseases)

        binding.btnAddChronicDiseasesPatientDetails.setOnClickListener {
            val data="Diabetes"
            chronicDiseases.add(data)
            displayChronicDiseasesList(chronicDiseases)
        }

        return binding.root
    }

    private fun displayChronicDiseasesList(chronicDiseases:List<String>){
        binding.chipsGroupChronicDiseasesPatientDetails.removeAllViews()
        for (disease in chronicDiseases) {
            val chip = Chip(binding.chipsGroupChronicDiseasesPatientDetails.context)
            chip.text = disease
            binding.chipsGroupChronicDiseasesPatientDetails.addView(chip)
        }
    }

}