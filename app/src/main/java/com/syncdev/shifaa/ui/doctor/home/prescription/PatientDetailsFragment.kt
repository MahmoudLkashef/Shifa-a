package com.syncdev.shifaa.ui.doctor.home.prescription

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentPatientDetailsBinding
import com.syncdev.shifaa.utils.Dialogs

class PatientDetailsFragment : Fragment() {

    private val TAG = "PatientDetailsFragment"
    private lateinit var binding: FragmentPatientDetailsBinding
    private val prescriptionViewModel by viewModels<PrescriptionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_patient_details,
            container,
            false
        )

        val prescriptionAdapter = PrescriptionAdapter()

        prescriptionViewModel.medicationList.observe(viewLifecycleOwner, Observer {
            prescriptionAdapter.submitList(it.toList())
        })


        binding.apply {

            rvMedicinePrescription.adapter=prescriptionAdapter

            viewPatientMedicalHistory.setOnClickListener {
                findNavController().navigate(
                    PatientDetailsFragmentDirections
                        .actionPatientDetailsFragmentToPatientMedicalHistoryFragment()
                )
            }

            fabAddNewMedicinePrescription.setOnClickListener {
                Dialogs().addNewMedicineToPrescriptionDialog(requireContext(),prescriptionViewModel)

            }

            btnBackPatientDetails.setOnClickListener {
                findNavController().popBackStack()
            }
        }


        return binding.root
    }

}