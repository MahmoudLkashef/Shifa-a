package com.syncdev.shifaa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.databinding.FragmentSignUpPatientBinding

class SignUpPatientFragment : Fragment() {


    private lateinit var binding: FragmentSignUpPatientBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpPatientBinding.inflate(inflater,container,false)

        binding.imgBackArrowSignupPatient.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnConfirmSignupPatient.setOnClickListener {
            findNavController().navigate(SignUpPatientFragmentDirections.actionSignUpPatientFragmentToSignInFragment())
        }
        return binding.root
    }

}