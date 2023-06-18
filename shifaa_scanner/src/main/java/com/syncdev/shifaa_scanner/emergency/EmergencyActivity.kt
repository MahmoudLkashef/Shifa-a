package com.syncdev.shifaa_scanner.emergency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.chip.Chip
import com.syncdev.shifaa_scanner.databinding.ActivityEmergencyBinding
import com.syncdev.shifaa_scanner.model.MedicalHistory

class EmergencyActivity : AppCompatActivity() {
    private val TAG="EmergencyActivity"
    private lateinit var binding:ActivityEmergencyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEmergencyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val medicalHistory = intent.getParcelableExtra<MedicalHistory>("MedicalHistory")

        binding.medicalHistory=medicalHistory

        medicalHistory?.let {
            displayEmergencyContacts(it.emergencyContacts)
            displayChronicDiseasesList(it.chronicDiseases)
        }
    }

    private fun displayChronicDiseasesList(chronicDiseases: List<String>) {
        binding.chipsGroupChronicDiseases.removeAllViews()
        for (disease in chronicDiseases) {
            val chip = Chip(binding.chipsGroupChronicDiseases.context)
            chip.text = disease
            binding.chipsGroupChronicDiseases.addView(chip)
        }
    }
    private fun displayEmergencyContacts(emergencyContacts: List<String>) {
        if(emergencyContacts.isNotEmpty()){
            binding.tvFirstEmergencyContact.text=emergencyContacts[0]
            binding.tvSecondEmergencyContact.text=emergencyContacts[1]
        }
    }
}