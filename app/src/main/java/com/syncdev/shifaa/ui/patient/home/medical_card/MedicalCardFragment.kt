package com.syncdev.shifaa.ui.patient.home.medical_card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.chip.Chip
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentMedicalCardBinding
import com.syncdev.shifaa.utils.Dialogs

class MedicalCardFragment : Fragment() {

    private val TAG="MedicalCardFragment"
    private lateinit var binding:FragmentMedicalCardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_medical_card,
            container,
            false
        )

        val chronicDiseases: List<String> = listOf("Diabetes", "Hypertension", "Asthma", "Arthritis")

        displayChronicDiseasesList(chronicDiseases)

        binding.btnEditEmergencyContacts.setOnClickListener {
            Dialogs().editEmergencyContactsDialog(requireContext())
        }

        return binding.root
    }

    private fun displayChronicDiseasesList(chronicDiseases:List<String>){
        for (disease in chronicDiseases) {
            val chip = Chip(binding.chipsGroupChronicDiseases.context)
            chip.text = disease
            binding.chipsGroupChronicDiseases.addView(chip)
        }
    }

}