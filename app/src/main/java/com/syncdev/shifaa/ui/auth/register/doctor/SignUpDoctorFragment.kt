package com.syncdev.shifaa.ui.auth.register.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.syncdev.shifaa.utils.Validation
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentSignUpDoctorBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpDoctorFragment : Fragment() {

    private val TAG = "SignUpDoctorFragment"
    private lateinit var binding: FragmentSignUpDoctorBinding
    private var validFirstName = false
    private var validLastName = false
    private var validEmail = false
    private var validSpeciality = false
    private var validGender = false
    private var validPhoneNumber = false
    private var validPassword = false
    private var validConfirmPassword = false
    private val doctorViewModel by viewModels<SignUpDoctorViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpDoctorBinding.inflate(inflater, container, false)

        val specialty = resources.getStringArray(R.array.specialty)
        specialty.sort()
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, specialty)

        with(binding) {
            viewModel = doctorViewModel

            dropdownMenuDoctorSpecialty.setAdapter(arrayAdapter)

            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnDoctorSignup.setOnClickListener {
                if (validaData()) {
                    doctorViewModel.registerDoctor()
                    loadingButton()
                }else{
                    Snackbar.make(requireView(),"Please Fill Missing Fields", Snackbar.ANIMATION_MODE_SLIDE).show()
                }
            }

            radoiGroupDoctorGender.setOnCheckedChangeListener { _, checkID ->
                when (checkID) {
                    R.id.radio_btn_doctor_male -> {
                        doctorViewModel.gender.value = "Male"
                        validGender = true
                    }
                    R.id.radio_btn_doctor_female -> {
                        doctorViewModel.gender.value = "Female"
                        validGender = true
                    }
                }
            }

            dropdownMenuDoctorSpecialty.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    val selectedSpecialty =
                        dropdownMenuDoctorSpecialty.adapter.getItem(position) as String
                    doctorViewModel.speciality.value = selectedSpecialty
                }

            doctorViewModel.navigate.observe(viewLifecycleOwner, Observer { validInputs ->
                if (validInputs) {
                    findNavController().navigate(SignUpDoctorFragmentDirections.actionSignUpDoctorFragmentToSignInFragment())
                }
            })

            doctorViewModel.firstName.observe(viewLifecycleOwner, Observer { firstName->
                validFirstName = Validation.validateName(firstName,binding.tilFnameDoctor)
            })

            doctorViewModel.lastName.observe(viewLifecycleOwner, Observer { lastName->
                validLastName = Validation.validateName(lastName,binding.tilLnameDoctor)
            })

            doctorViewModel.email.observe(viewLifecycleOwner, Observer { email->
                validEmail = Validation.validateEmail(email,binding.tilDoctorEmail)
            })

            doctorViewModel.phoneNumber.observe(viewLifecycleOwner, Observer { phoneNumber->
                validPhoneNumber = Validation.validatePhoneNumber(phoneNumber,binding.tilDoctorMobileNumber)
            })

            doctorViewModel.password.observe(viewLifecycleOwner, Observer { password->
                validPassword = Validation.validatePassword(password,binding.tilDoctorPassword)
            })

            doctorViewModel.confirmPassword.observe(viewLifecycleOwner, Observer { confirmPassword->
                validConfirmPassword = Validation.validateConfirmPassword(
                    confirmPassword,
                    doctorViewModel.password.value,
                    binding.itlDoctorConfirmPassword
                )
            })

            doctorViewModel.speciality.observe(viewLifecycleOwner, Observer { selectedSpeciality->
                validSpeciality = Validation.validateSpeciality(
                    selectedSpeciality,
                    specialty,
                    binding.tilDocorSpecialty
                )
            })

            return binding.root
        }
    }

    private fun validaData(): Boolean{
        return validFirstName and validLastName and validSpeciality and
                validEmail and validPassword and validPhoneNumber and
                validConfirmPassword and validGender
    }

    private fun loadingButton(){
        binding.btnDoctorSignup.isEnabled = false
        binding.btnDoctorSignup.text = "Registering..."
        binding.btnDoctorSignup.setTextColor(resources.getColor(R.color.white))
    }
}