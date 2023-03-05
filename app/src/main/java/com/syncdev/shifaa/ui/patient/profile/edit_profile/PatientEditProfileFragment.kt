package com.syncdev.shifaa.ui.patient.profile.edit_profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentPatientEditProfileBinding
import java.util.*


class PatientEditProfileFragment : Fragment() {

    private val TAG="PatientEditProfileFragment"
    private lateinit var binding:FragmentPatientEditProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_patient_edit_profile,
            container,
            false
        )

        val datePickerListener = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            binding.etEditPatientBirthDate.setText("$day/$month/$year")
        }

        binding.etEditPatientBirthDate.setOnClickListener {
            showDateDialog(datePickerListener)
        }

        binding.btnBackPatientEditProfile.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnCancelPatientEditProfile.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun showDateDialog(listener: DatePickerDialog.OnDateSetListener) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(),R.style.DatePickerDialogTheme,listener,year,month,day)

        datePickerDialog.show()
    }
}