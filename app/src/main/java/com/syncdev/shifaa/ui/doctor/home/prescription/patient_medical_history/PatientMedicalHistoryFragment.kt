package com.syncdev.shifaa.ui.doctor.home.prescription.patient_medical_history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.syncdev.domain.model.MedicalHistory
import com.syncdev.domain.model.Medication
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentPatientMedicalHistoryBinding
import com.syncdev.shifaa.ui.doctor.home.prescription.PatientDetailsFragmentArgs
import com.syncdev.shifaa.utils.Dialogs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientMedicalHistoryFragment : Fragment() {
    private val TAG = "PatientMedicalHistoryFragment"
    private lateinit var binding: FragmentPatientMedicalHistoryBinding
    private val patientMedicalHistoryViewModel by viewModels<PatientMedicalHistoryViewModel>()
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

        val args: PatientMedicalHistoryFragmentArgs by navArgs()
        val patientId = args.patientId

        patientMedicalHistoryViewModel.getPatientMedicalHistory(patientId)


        val chronicDiseases = mutableListOf<String>()


        patientMedicalHistoryViewModel.patientMedicalHistoryData.observe(
            viewLifecycleOwner,
            Observer {
                Log.i(TAG, "onCreateView: $it")
                setDataOnViews(it)
                chronicDiseases.addAll(it.chronicDiseases)
                displayChronicDiseasesList(chronicDiseases)
            })

        val patientMedicationAdapter=PatientMedicationAdapter()

        val medicationListTest= listOf<Medication>(
            Medication("","Panadol Cold Flue","Tablets","3","500","3",
                listOf("After Breakfast")
            ),
            Medication("","Panadol Cold Flue","Tablets","3","500","3",
                listOf("After Breakfast")
            ),
            Medication("","Panadol Cold Flue","Tablets","3","500","3",
                listOf("After Breakfast")
            )
        )

        patientMedicationAdapter.submitList(medicationListTest)

        binding.apply {

            rvPatientMedicationsPatientDetails.adapter=patientMedicationAdapter

            btnBackPatientMedicalHistory.setOnClickListener {
                findNavController().popBackStack()
            }

            btnAddChronicDiseasesPatientDetails.setOnClickListener {
                Dialogs().showAddNewChronicDiseasesDialog(requireContext()) { chronicDisease ->
                    chronicDiseases.add(chronicDisease)
                    displayChronicDiseasesList(chronicDiseases)
                    patientMedicalHistoryViewModel.updatePatientChronicDiseases(
                        patientId,
                        chronicDiseases
                    )
                }
            }

            btnUpdatePatientDetails.setOnClickListener {
                Dialogs().showUpdatePatientMedicalCardDialog(requireContext()) { bloodType, height, weight ->
                    val medicalHistory =
                        MedicalHistory("", bloodType, "", height, weight, emptyList(), emptyList())

                    updateMedicalCardData(medicalHistory)

                    patientMedicalHistoryViewModel.updatePatientMedicalHistory(
                        patientId,
                        medicalHistory
                    )
                }
            }
        }

        return binding.root
    }

    private fun displayChronicDiseasesList(chronicDiseases: List<String>) {
        binding.chipsGroupChronicDiseasesPatientDetails.removeAllViews()
        for (disease in chronicDiseases) {
            val chip = Chip(binding.chipsGroupChronicDiseasesPatientDetails.context)
            chip.text = disease
            binding.chipsGroupChronicDiseasesPatientDetails.addView(chip)
        }
    }

    private fun setDataOnViews(medicalHistory: MedicalHistory) {
        binding.apply {
            tvAgePatientDetails.text = medicalHistory.age.takeIf { it.isNotEmpty() }
            tvBloodTypePatientDetails.text = medicalHistory.bloodType.takeIf { it.isNotEmpty() }
            tvHeightPatientDetails.text = medicalHistory.height.takeIf { it.isNotEmpty() }
            tvWeightPatientDetails.text = medicalHistory.weight.takeIf { it.isNotEmpty() }
        }
    }

    private fun updateMedicalCardData(medicalHistory: MedicalHistory){
        binding.apply {
            if(medicalHistory.bloodType.isNotEmpty())tvBloodTypePatientDetails.text=medicalHistory.bloodType
            if(medicalHistory.weight.isNotEmpty())tvWeightPatientDetails.text=medicalHistory.weight
            if(medicalHistory.height.isNotEmpty())tvHeightPatientDetails.text=medicalHistory.height
        }
    }

}