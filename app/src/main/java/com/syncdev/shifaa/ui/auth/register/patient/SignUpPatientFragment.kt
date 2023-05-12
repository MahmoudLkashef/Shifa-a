package com.syncdev.shifaa.ui.auth.register.patient

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentSignUpPatientBinding
import com.syncdev.shifaa.utils.DateUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpPatientFragment : Fragment() {

    private lateinit var binding: FragmentSignUpPatientBinding
    private val TAG = "SignUpPatientFragment"
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
                    R.id.radio_btn_patient_male -> { patientSignUpViewModel.gender.value = "Male"}
                    R.id.radio_btn_patient_female -> {patientSignUpViewModel.gender.value = "Female"}
                }
            }

            dropDownMenuDay.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position) as String
                Log.i(TAG, "onCreateView: $selectedItem")
                patientSignUpViewModel.day.value = selectedItem
            }

            dropDownMenuMonth.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position) as String
                Log.i(TAG, "onCreateView: $selectedItem")
                patientSignUpViewModel.month.value = selectedItem
            }

            dropDownMenuYear.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position) as String
                Log.i(TAG, "onCreateView: $selectedItem")
                patientSignUpViewModel.year.value = selectedItem
            }

            imgBackArrowSignupPatient.setOnClickListener {
                findNavController().popBackStack()
            }

            btnConfirmSignupPatient.setOnClickListener {
                if (validaPassword()){
                    patientSignUpViewModel.signUpPatient()
                }
            }
        }

        patientSignUpViewModel.navigate.observe(viewLifecycleOwner, Observer { validData ->
            if (validData){
                findNavController().navigate(SignUpPatientFragmentDirections.actionSignUpPatientFragmentToSignInFragment())
            }
        })

        return binding.root
    }

    private fun validaPassword(): Boolean{
        val pass = binding.etPasswordPatient.text.toString()
        val confirmPass = binding.etConfirmPasswordPatient.text.toString()
        return pass == confirmPass
    }
}