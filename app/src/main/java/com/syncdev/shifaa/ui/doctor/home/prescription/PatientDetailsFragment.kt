package com.syncdev.shifaa.ui.doctor.home.prescription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.syncdev.domain.model.Prescription
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentPatientDetailsBinding
import com.syncdev.shifaa.utils.DateUtils
import com.syncdev.shifaa.utils.Dialogs
import com.syncdev.shifaa.utils.Validation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        val args: PatientDetailsFragmentArgs by navArgs()
        val appointmentId=args.appointmentId
        val doctorId=args.doctorId
        val patientId=args.patientId
        val patientComment=args.comment
        val doctorName=args.doctorName
        val doctorEmail=args.doctorEmail
        val doctorPhone=args.doctorPhone
        val doctorSpecialty=args.doctorSpecialty

        val prescriptionAdapter = PrescriptionAdapter()

        prescriptionViewModel.medicationList.observe(viewLifecycleOwner, Observer {
            prescriptionAdapter.submitList(it.toList())
        })


        prescriptionViewModel.savedState.observe(viewLifecycleOwner, Observer {saved->
            if(saved)findNavController().popBackStack()
        })

        binding.apply {

            tvPatientCommentPatientDetails.text=patientComment
            tvDoctorNamePrescription.text=doctorName
            tvDoctorNumberPrescription.text=doctorPhone
            tvDoctorSpecialtyPrescription.text=doctorSpecialty
            tvDoctorEmailPrescription.text=doctorEmail
            tvPrescriptionDate.text=DateUtils.getCurrentDateNumber()

            rvMedicinePrescription.adapter = prescriptionAdapter

            viewPatientMedicalHistory.setOnClickListener {
                findNavController().navigate(
                    PatientDetailsFragmentDirections
                        .actionPatientDetailsFragmentToPatientMedicalHistoryFragment(patientId)
                )
            }

            fabAddNewMedicinePrescription.setOnClickListener {
                Dialogs().addNewMedicineToPrescriptionDialog(
                    requireContext(),
                    prescriptionViewModel
                )
            }

            btnSavePrescription.setOnClickListener {
                if (validInputs()) {
                    val prescription = Prescription(
                        "",
                        patientId,
                        doctorId,
                        prescriptionViewModel.medicationList.value?.toList()!!,
                        etPrescriptionNote.text.toString()
                    )
                    prescriptionViewModel.savePatientPrescription(prescription,appointmentId,patientId)
                }
            }

            btnBackPatientDetails.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        return binding.root
    }

    private fun validMedication(): Boolean {
        return if (binding.rvMedicinePrescription.size > 0) {
            return true
        } else {
            Snackbar.make(
                requireView(),
                "Please Add Medicine to Prescription",
                Snackbar.LENGTH_LONG
            ).show()
            false
        }
    }

    private fun validInputs(): Boolean {
        return Validation.validateNote(
            binding.etPrescriptionNote.text.toString(),
            binding.tilPrescriptionNote
        ) and validMedication()
    }

}