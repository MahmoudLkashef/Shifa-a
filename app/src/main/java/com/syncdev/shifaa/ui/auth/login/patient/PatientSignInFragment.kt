package com.syncdev.shifaa.ui.auth.login.patient

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentPatientSignInBinding
import com.syncdev.shifaa.ui.auth.login.SignInFragmentDirections
import com.syncdev.shifaa.ui.patient.PatientActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientSignInFragment : Fragment() {

    private lateinit var binding: FragmentPatientSignInBinding
    private val patientSignInViewModel by viewModels<PatientSignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_patient_sign_in,
            container,
            false
        )

        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager_signin)

        with(binding){
            viewModel = patientSignInViewModel
            tvPatientResetPassword.setOnClickListener {
                findNavController().navigate(
                    SignInFragmentDirections.actionSignInFragmentToForgetPasswordFragment()
                )
            }

            tvPatientRegister.setOnClickListener {
                findNavController().navigate(
                    SignInFragmentDirections.actionSignInFragmentToSignUpPatientFragment()
                )
            }

            viewSwipeToDoctorCircle.setOnClickListener {
                viewPager.currentItem = 1
            }

            btnPatientSignin.setOnClickListener {
                patientSignInViewModel.loginPatient()
            }
        }

        patientSignInViewModel.navigate.observe(viewLifecycleOwner, Observer { validUser ->
            if (validUser){
                startActivity(Intent(requireContext(), PatientActivity::class.java))
                activity?.finish()
            }
        })

        return binding.root
    }


}