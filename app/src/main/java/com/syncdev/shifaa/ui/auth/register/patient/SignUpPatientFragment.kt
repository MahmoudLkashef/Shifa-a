package com.syncdev.shifaa.ui.auth.register.patient

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentSignUpPatientBinding
import com.syncdev.shifaa.utils.DateUtils
import com.syncdev.shifaa.utils.Validation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpPatientFragment : Fragment() {

    private lateinit var binding: FragmentSignUpPatientBinding
    private val TAG = "SignUpPatientFragment"
    private var validEmail = false
    private var validDay = false
    private var validMonth = false
    private var validYear = false
    private var validGender = false
    private var validPhoneNumber = false
    private var validPassword = false
    private var validConfirmPassword = false
    private val patientSignUpViewModel by viewModels<SignUpPatientViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_sign_up_patient,
            container,
            false
        )

        val dayAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, DateUtils.get31DayList())
        val month = resources.getStringArray(R.array.month)
        val monthAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, month)
        val yearAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, DateUtils.getLast100Years())

        with(binding){
            viewModel = patientSignUpViewModel
            dropDownMenuDay.setAdapter(dayAdapter)
            dropDownMenuMonth.setAdapter(monthAdapter)
            dropDownMenuYear.setAdapter(yearAdapter)

            radioGroup.setOnCheckedChangeListener { _, checkID ->
                when(checkID){
                    R.id.radio_btn_patient_male -> {
                        patientSignUpViewModel.gender.value = "Male"
                        validGender = true
                    }
                    R.id.radio_btn_patient_female -> {
                        patientSignUpViewModel.gender.value = "Female"
                        validGender = true
                    }
                }
            }

            dropDownMenuDay.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position) as String
                Log.i(TAG, "onCreateView: $selectedItem")
                patientSignUpViewModel.day.value = selectedItem
            }

            dropDownMenuMonth.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position) as String
                Log.i(TAG, "onCreateView: $selectedItem")
                patientSignUpViewModel.month.value = selectedItem
            }

            dropDownMenuYear.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position) as String
                Log.i(TAG, "onCreateView: $selectedItem")
                patientSignUpViewModel.year.value = selectedItem
            }

            imgBackArrowSignupPatient.setOnClickListener {
                findNavController().popBackStack()
            }

            btnConfirmSignupPatient.setOnClickListener {
                if (validaData()) {
                    patientSignUpViewModel.signUpPatient()
                }else{
                    Snackbar.make(requireView(),"Please Fill Missing Fields",Snackbar.ANIMATION_MODE_SLIDE).show()
                }
            }
        }


        patientSignUpViewModel.email.observe(viewLifecycleOwner, Observer { email->
            validEmail =Validation.validateEmail(email,binding.tilEmailPatient)
        })

        patientSignUpViewModel.phoneNumber.observe(viewLifecycleOwner, Observer {phoneNumber->
            validPhoneNumber = Validation.validatePhoneNumber(phoneNumber,binding.tilMobileNumberPatient)
        })

        patientSignUpViewModel.password.observe(viewLifecycleOwner, Observer { password->
            validPassword = Validation.validatePassword(password,binding.tilPasswordPatient)
        })

        patientSignUpViewModel.confirmPassword.observe(viewLifecycleOwner, Observer { confirmPassword->
            validConfirmPassword = Validation.validateConfirmPassword(
                confirmPassword,
                patientSignUpViewModel.password.value,
                binding.tilConfirmPasswordPatient
            )
        })

        patientSignUpViewModel.day.observe(viewLifecycleOwner, Observer { day->
            validDay = Validation.validateDay(day,binding.tilDaySignupPatient)
        })

        patientSignUpViewModel.month.observe(viewLifecycleOwner, Observer { selectedMonth->
            validMonth = Validation.validateMonth(
                selectedMonth,
                month,
                binding.tilMonthSignupPatient
            )
        })

        patientSignUpViewModel.year.observe(viewLifecycleOwner, Observer { year->
            validYear = Validation.validateYear(year,binding.tilYearSignupPatient)
        })



        patientSignUpViewModel.navigate.observe(viewLifecycleOwner, Observer { validData ->
            if (validData){
                findNavController().navigate(SignUpPatientFragmentDirections.actionSignUpPatientFragmentToSignInFragment())
            }
        })

        return binding.root
    }

    private fun validaData(): Boolean{
        return validDay and validMonth and validYear and validEmail and validPassword and validConfirmPassword and validGender
    }
}