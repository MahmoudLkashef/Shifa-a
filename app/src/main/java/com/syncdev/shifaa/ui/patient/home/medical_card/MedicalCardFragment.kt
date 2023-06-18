package com.syncdev.shifaa.ui.patient.home.medical_card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.syncdev.domain.model.MedicalHistory
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentMedicalCardBinding
import com.syncdev.shifaa.utils.Dialogs
import com.syncdev.shifaa.utils.qrcode.QrCode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicalCardFragment : Fragment() {

    private val TAG="MedicalCardFragment"
    private lateinit var binding:FragmentMedicalCardBinding
    private val medicalCardViewModel by viewModels<MedicalCardViewModel>()
    private lateinit var medicalCardData:MedicalHistory

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


        medicalCardViewModel.getPatientMedicalHistory()

        medicalCardViewModel.patientMedicalHistory.observe(viewLifecycleOwner, Observer {medicalHistory->
            setDataOnViews(medicalHistory)
            medicalCardData=medicalHistory
        })

        binding.btnEditEmergencyContacts.setOnClickListener {
            Dialogs().editEmergencyContactsDialog(requireContext()){firstContact,secondContact->
                binding.tvFirstEmergencyContact.text=firstContact
                binding.tvSecondEmergencyContact.text=secondContact
                medicalCardViewModel.updateEmergencyContacts(listOf(firstContact,secondContact))
            }
        }

        binding.imgQrCodeCard.setOnClickListener {
            val serializeMedicalCard=QrCode.serializeMedicalCard(medicalCardData,"MedicalCard")
            val qrCode=QrCode.generateQRCode(serializeMedicalCard)
            if(qrCode!=null){
                Dialogs().showMedicalCardQrCode(requireContext(),qrCode)
            }
        }

        binding.btnBackMedicalCard.setOnClickListener {
            findNavController().popBackStack()
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

    private fun setDataOnViews(medicalHistory: MedicalHistory) {
        binding.apply {
            tvPatientNameMedicalCard.text=medicalCardViewModel.getPatientName()
            tvBloodTypeMedicalCard.text=medicalHistory.bloodType
            tvAgeMedicalCard.text="${medicalHistory.age} Years"
            tvWeightMedicalCard.text="${medicalHistory.weight} KG"
            tvHeightMedicalCard.text="${medicalHistory.height} CM"
            displayChronicDiseasesList(medicalHistory.chronicDiseases)
            if(medicalHistory.emergencyContacts.isNotEmpty()){
                tvFirstEmergencyContact.text= medicalHistory.emergencyContacts[0]
                tvSecondEmergencyContact.text= medicalHistory.emergencyContacts[1]
            }
        }
    }

}