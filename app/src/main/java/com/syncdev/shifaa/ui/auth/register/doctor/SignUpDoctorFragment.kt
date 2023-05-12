package com.syncdev.shifaa.ui.auth.register.doctor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.navigation.fragment.findNavController
import com.syncdev.domain.model.Doctor
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentSignUpDoctorBinding


class SignUpDoctorFragment : Fragment() {

    private val TAG="SignUpDoctorFragment"
    private lateinit var binding: FragmentSignUpDoctorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpDoctorBinding.inflate(inflater,container,false)

        val specialty = resources.getStringArray(R.array.specialty)
        specialty.sort()
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,specialty)
        binding.dropdownMenuDoctorSpecialty.setAdapter(arrayAdapter)

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnDoctorSignup.setOnClickListener {

            val doctorData=getDoctorData()
            val password=binding.etDoctorPassword.text.toString()
            val confirmPassword=binding.etDoctorConfirmPassword.text.toString()
            if(password == confirmPassword) {
                // call UseCase
                Log.i(TAG, ""+doctorData)
            }

            findNavController().navigate(SignUpDoctorFragmentDirections.actionSignUpDoctorFragmentToSignInFragment())
        }

        return binding.root
    }

    private fun getDoctorData():Doctor{
        val firstName =binding.etFnameDoctor.text.toString()
        val lastName=binding.etLnameDoctor.text.toString()
        val gender=getDoctorGender()
        val speciality=binding.dropdownMenuDoctorSpecialty.text.toString()
        val phoneNumber=binding.etDoctorMobileNumber.text.toString()
        val email=binding.etDoctorEmail.text.toString()

        return Doctor(
            firstName=firstName,
            lastName = lastName,
            gender = gender,
            phoneNumber = phoneNumber,
            email = email,
            speciality = speciality
        )
    }
    private fun getDoctorGender():String{
        val selectedId = binding.radoiGroupDoctorGender.checkedRadioButtonId

        return if (selectedId != -1) {
            val selectedRadioButton = binding.root.findViewById<RadioButton>(selectedId)
            val selectedGender = selectedRadioButton.text.toString()
            selectedGender
        } else ""
    }


}