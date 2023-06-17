package com.syncdev.shifaa.ui.doctor.home.prescription.patient_medical_history

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
import com.syncdev.shifaa.utils.Dialogs

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

        val chronicDiseases = mutableListOf<String>("Hypertension")

        displayChronicDiseasesList(chronicDiseases)

        binding.apply {

            btnBackPatientMedicalHistory.setOnClickListener {
                findNavController().popBackStack()
            }

            btnAddChronicDiseasesPatientDetails.setOnClickListener {
                Dialogs().showAddNewChronicDiseasesDialog(requireContext()){chronicDisease ->
                    chronicDiseases.add(chronicDisease)
                    displayChronicDiseasesList(chronicDiseases)
                }
            }

            btnUpdatePatientDetails.setOnClickListener {
                Dialogs().showUpdatePatientMedicalCardDialog(requireContext()){bloodType,height,weight->
                    binding.tvBloodTypePatientDetails.text=bloodType
                    binding.tvHeightPatientDetails.text=height
                    binding.tvWeightPatientDetails.text=weight
                }
            }
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