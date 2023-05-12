package com.syncdev.shifaa.ui.auth.login.doctor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.syncdev.domain.utils.Constants
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentDoctorSignInBinding
import com.syncdev.shifaa.ui.auth.login.SignInFragmentDirections
import com.syncdev.shifaa.ui.doctor.DoctorActivity
import com.syncdev.shifaa.utils.Validation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorSignInFragment : Fragment() {
    private val TAG = "DoctorSignInFragment"

    private lateinit var binding: FragmentDoctorSignInBinding
    private var validEmail = false
    private var validPassword = false
    private val doctorViewModel by viewModels<DoctorSignInViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_doctor_sign_in,
            container,
            false
        )

        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager_signin)

        with(binding) {

            viewModel=doctorViewModel

            tvDoctorResetPassword.setOnClickListener {
                findNavController().navigate(
                    SignInFragmentDirections.actionSignInFragmentToForgetPasswordFragment("Doctor")
                )
            }
            tvDoctorRegister.setOnClickListener {
                findNavController().navigate(
                    SignInFragmentDirections.actionSignInFragmentToSignUpDoctorFragment()
                )
            }

            viewSwipeToPatientCircle.setOnClickListener {
                viewPager.currentItem = 0
            }

            btnDoctorSignin.setOnClickListener {
                if (validaData()){
                    doctorViewModel.loginDoctor()
                    loadingButton()
                }else{
                    Snackbar.make(requireView(),"Fill Missing Field", Snackbar.ANIMATION_MODE_SLIDE).show()
                }
            }
        }

        doctorViewModel.navigate.observe(viewLifecycleOwner, Observer { isAuthenticated ->
            if (isAuthenticated) {
                startActivity(Intent(requireContext(), DoctorActivity::class.java))
                activity?.finish()
            }
        })

        doctorViewModel.email.observe(viewLifecycleOwner, Observer { email ->
            validEmail = Validation.isEmptyTextInput(email,binding.tilDoctorEmail)
        })

        doctorViewModel.password.observe(viewLifecycleOwner, Observer { password ->
            validPassword = Validation.isEmptyTextInput(password,binding.tilDoctorPassword)
        })

        return binding.root
    }

    private fun validaData(): Boolean{
        return validEmail and validPassword
    }

    private fun loadingButton(){
        binding.btnDoctorSignin.isEnabled = false
        binding.btnDoctorSignin.text = "Logging in..."
        binding.btnDoctorSignin.setTextColor(resources.getColor(R.color.white))
    }

}