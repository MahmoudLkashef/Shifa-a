package com.syncdev.shifaa.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.syncdev.shifaa.databinding.FragmentSignInBinding
import com.syncdev.shifaa.ui.doctor.DoctorActivity
import com.syncdev.shifaa.ui.patient.PatientActivity


class SignInFragment : Fragment() {


    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater,container,false)

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpRoleFragment())
        }

        binding.tvResetPassword.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToForgetPasswordFragment())
        }

        binding.btnSignin.setOnClickListener {
            navigateTo(userName = getUserName())
        }

        return binding.root
    }

    private fun getUserName(): String{
        return binding.etEmail.text.toString()
    }

    private fun navigateTo(userName: String){
        when(userName){
            "doctor" -> {
                startActivity(Intent(requireContext(),DoctorActivity::class.java))
                activity?.finish()
            }
            "patient" -> {
                startActivity(Intent(requireContext(),PatientActivity::class.java))
                activity?.finish()
            }
            else -> {
                Snackbar.make(requireView(), "Invalid User",Snackbar.ANIMATION_MODE_SLIDE).show()
            }
        }
    }

}