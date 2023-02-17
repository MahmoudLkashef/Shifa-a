package com.syncdev.shifaa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.databinding.FragmentSignUpDoctorBinding


class SignUpDoctorFragment : Fragment() {


    private lateinit var binding: FragmentSignUpDoctorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpDoctorBinding.inflate(inflater,container,false)

        binding.buttonBack.setOnClickListener {
            findNavController().navigate(SignUpDoctorFragmentDirections.actionSignUpDoctorFragmentToSignUpRoleFragment())
        }

        binding.btnDoctorSignup.setOnClickListener {
            findNavController().navigate(SignUpDoctorFragmentDirections.actionSignUpDoctorFragmentToSignInFragment())
        }

        return binding.root
    }


}