package com.syncdev.shifaa.ui.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentSignUpDoctorBinding


class SignUpDoctorFragment : Fragment() {


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
            findNavController().navigate(SignUpDoctorFragmentDirections.actionSignUpDoctorFragmentToSignInFragment())
        }

        return binding.root
    }


}