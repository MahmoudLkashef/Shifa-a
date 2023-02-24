package com.syncdev.shifaa.ui.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.databinding.FragmentSignUpRoleBinding

class SignUpRoleFragment : Fragment() {

    private lateinit var binding: FragmentSignUpRoleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpRoleBinding.inflate(inflater,container,false)

        binding.CardViewPatientRole.setOnClickListener {
            findNavController().navigate(SignUpRoleFragmentDirections.actionSignUpRoleFragmentToSignUpPatientFragment())
        }

        binding.CardViewDoctorRole.setOnClickListener {
            findNavController().navigate(SignUpRoleFragmentDirections.actionSignUpRoleFragmentToSignUpDoctorFragment())
        }

        binding.buttonBackSignUpRole.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
}