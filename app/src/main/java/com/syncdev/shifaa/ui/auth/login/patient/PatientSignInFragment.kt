package com.syncdev.shifaa.ui.auth.login.patient

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentPatientSignInBinding
import com.syncdev.shifaa.ui.auth.login.SignInFragmentDirections
import com.syncdev.shifaa.ui.patient.PatientActivity
import com.syncdev.shifaa.utils.Internet
import com.syncdev.shifaa.utils.Validation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientSignInFragment : Fragment() {

    private lateinit var binding: FragmentPatientSignInBinding
    private var validEmail = false
    private var validPassword = false
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
                    SignInFragmentDirections.actionSignInFragmentToForgetPasswordFragment("Patient")
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
                if (validaData()){
                    validateUser()
                }else{
                    Snackbar.make(requireView(),"Fill Missing Field",Snackbar.ANIMATION_MODE_SLIDE).show()
                }
            }
        }

        patientSignInViewModel.navigate.observe(viewLifecycleOwner, Observer { validUser ->
            if (validUser){
                startActivity(Intent(requireContext(), PatientActivity::class.java))
                activity?.finish()
            }
        })

        patientSignInViewModel.email.observe(viewLifecycleOwner, Observer { email ->
            validEmail = Validation.isEmptyTextInput(email,binding.tilPatientEmail)
        })

        patientSignInViewModel.password.observe(viewLifecycleOwner, Observer { password ->
            validPassword = Validation.isEmptyTextInput(password,binding.tilPatientPassword)
        })

        return binding.root
    }

    private fun validaData(): Boolean{
        return validEmail and validPassword
    }

    private fun validateUser(){
        if (checkInternetConnectivity()) {
            patientSignInViewModel.loginPatient()
            loadingButton()
            patientSignInViewModel.firebaseUser.observe(viewLifecycleOwner, Observer { user ->
                if (user == null) {
                    binding.tilPatientEmail.error = "Incorrect Email or Password"
                    resetButton()
                }
            })
        }else{
            Snackbar.make(requireView(),"Check Your Internet Connection", Snackbar.ANIMATION_MODE_SLIDE)
                .setAction("Retry") { validateUser() }
                .show()
        }
    }

    private fun checkInternetConnectivity():Boolean{
        return Internet.isInternetConnected(requireContext().applicationContext)
    }

    private fun loadingButton(){
        binding.btnPatientSignin.isEnabled = false
        binding.btnPatientSignin.text = "Logging in..."
        binding.btnPatientSignin.setTextColor(resources.getColor(R.color.white))
    }

    private fun resetButton(){
        binding.btnPatientSignin.isEnabled = true
        binding.btnPatientSignin.text = "Sign In"
        binding.btnPatientSignin.setTextColor(resources.getColor(R.color.white))
    }
}