package com.syncdev.shifaa.ui.patient.profile.edit_profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.syncdev.domain.model.Patient
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentPatientEditProfileBinding
import com.syncdev.shifaa.utils.Internet
import com.syncdev.shifaa.utils.Validation
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PatientEditProfileFragment : Fragment() {

    private val TAG = "PatientEditProfileFragment"
    private lateinit var binding: FragmentPatientEditProfileBinding
    private val patientEditProfileViewModel by viewModels<PatientEditProfileViewModel>()
    private var validFirstName = false
    private var validLastName = false
    private var validPhoneNumber = false
    private var validAge = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_patient_edit_profile,
            container,
            false
        )

        patientEditProfileViewModel.getPatientData()

        patientEditProfileViewModel.updateState.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                findNavController().popBackStack()
            })

        binding.apply {

            viewModel = patientEditProfileViewModel

            val datePickerListener = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                etEditPatientBirthDate.setText("$day-$month-$year")
            }

            etEditPatientBirthDate.setOnClickListener {
                showDateDialog(datePickerListener)
            }

            btnBackPatientEditProfile.setOnClickListener {
                findNavController().popBackStack()
            }

            btnCancelPatientEditProfile.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSavePatientEditProfile.setOnClickListener {
                validateAndUpdateDoctorData()
            }
        }

        return binding.root
    }

    private fun showDateDialog(listener: DatePickerDialog.OnDateSetListener) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DatePickerDialogTheme,
            listener,
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun validaData(): Boolean {
        return validFirstName and validLastName and validPhoneNumber and validAge
    }

    private fun hasInternetConnection(): Boolean {
        return Internet.isInternetConnected(requireContext().applicationContext)
    }

    private fun validateAndUpdateDoctorData() {
        validator()
        if (validaData()) {
            if (hasInternetConnection()) {
                patientEditProfileViewModel.updatePatientData()
            } else {
                Snackbar.make(
                    requireView(),
                    "Check Your Internet Connection",
                    Snackbar.ANIMATION_MODE_SLIDE
                )
                    .setAction("Retry") { validateAndUpdateDoctorData() }
                    .show()
            }
        }
    }

    private fun validator() {
        validFirstName = Validation.validateName(
            patientEditProfileViewModel.firstName.value.toString(),
            binding.tilEditPatientFirstName
        )
        validLastName = Validation.validateName(
            patientEditProfileViewModel.lastName.value.toString(),
            binding.tilEditPatientLastName
        )
        validPhoneNumber =
            Validation.validateName(
                patientEditProfileViewModel.phoneNumber.value.toString(),
                binding.tilEditPatientPhoneNumber
            )
        validAge = Validation.validateName(
            patientEditProfileViewModel.age.value.toString(),
            binding.tilEditPatientBirthDate
        )
    }


}