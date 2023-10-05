package com.syncdev.shifaa.ui.doctor.profile.edit_profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentDoctorEditProfileBinding
import com.syncdev.shifaa.utils.Internet
import com.syncdev.shifaa.utils.Validation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorEditProfileFragment : Fragment() {
    private val TAG = "DoctorEditProfileFragment"
    private lateinit var binding: FragmentDoctorEditProfileBinding
    private val doctorEditProfileViewModel by viewModels<DoctorEditProfileViewModel>()
    private var validFirstName = false
    private var validLastName = false
    private var validPhoneNumber = false
    private var validYearsOfExperience = false
    private var validAboutDoctor = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_doctor_edit_profile,
            container,
            false
        )

        binding.lifecycleOwner=this

        doctorEditProfileViewModel.getDoctorData()

        val specialty: MutableList<String> =
            resources.getStringArray(R.array.specialty).toMutableList()
        specialty.sort()
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, specialty)

        doctorEditProfileViewModel.updateState.observe(viewLifecycleOwner, Observer { updated ->
            if (updated) {
                findNavController().popBackStack()
            }
        })

        doctorEditProfileViewModel.firstName.observe(viewLifecycleOwner, Observer {
            validFirstName =
                Validation.validateName(it, binding.tilEditDoctorFirstName)
        })
        doctorEditProfileViewModel.lastName.observe(viewLifecycleOwner, Observer {
            validLastName = Validation.validateName(it, binding.tilEditDoctorLastName)
        })
        doctorEditProfileViewModel.phoneNumber.observe(viewLifecycleOwner, Observer {
            validPhoneNumber =
                Validation.validatePhoneNumber(it, binding.tilEditDoctorPhoneNumber)
        })
        doctorEditProfileViewModel.yearsOfExperience.observe(viewLifecycleOwner, Observer {
            validYearsOfExperience = Validation.validateExperience(
                it.toString(),
                binding.tilEditDoctorExperience
            )
        })
        doctorEditProfileViewModel.aboutDoctor.observe(viewLifecycleOwner, Observer {
            validAboutDoctor =
                Validation.validateAboutMe(it, binding.tilAboutDoctor)
        })


        binding.apply {
            viewModel=doctorEditProfileViewModel
            dropdownMenuEditDoctorSpecialty.setAdapter(arrayAdapter)

            dropdownMenuEditDoctorSpecialty.setText(
                doctorEditProfileViewModel.specialty.value,
                false
            )

            dropdownMenuEditDoctorSpecialty.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    val selectedSpecialty =
                        dropdownMenuEditDoctorSpecialty.adapter.getItem(position) as String
                    doctorEditProfileViewModel.specialty.value = selectedSpecialty
                }

            btnCancelDoctorEditProfile.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSaveDoctorEditProfile.setOnClickListener {
                Log.i(TAG, "onCreateView: ${validaData()}")
                validateAndUpdateDoctorData()
            }

            buttonBackDoctorEditProfile.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        return binding.root
    }

    private fun validaData(): Boolean {
        return validFirstName and validLastName and validYearsOfExperience and
                validPhoneNumber and validAboutDoctor
    }

    private fun hasInternetConnection(): Boolean {
        return Internet.isInternetConnected(requireContext().applicationContext)
    }

    private fun validateAndUpdateDoctorData() {
        if(validaData()){
            if(hasInternetConnection()){
                doctorEditProfileViewModel.updateDoctorData()
            }else{
                Snackbar.make(
                    requireView(),
                    "Check Your Internet Connection",
                    Snackbar.ANIMATION_MODE_SLIDE)
                    .setAction("Retry") { validateAndUpdateDoctorData() }
                    .show()
            }
        }
    }
}