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
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentSignUpDoctorBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpDoctorFragment : Fragment() {

    private val TAG = "SignUpDoctorFragment"
    private lateinit var binding: FragmentSignUpDoctorBinding
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
                doctorViewModel.registerDoctor()
            }

            radoiGroupDoctorGender.setOnCheckedChangeListener { _, checkID ->
                when (checkID) {
                    R.id.radio_btn_doctor_male -> doctorViewModel.gender.value = "Male"
                    R.id.radio_btn_doctor_female -> doctorViewModel.gender.value = "Female"
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

            return binding.root
        }
    }
}