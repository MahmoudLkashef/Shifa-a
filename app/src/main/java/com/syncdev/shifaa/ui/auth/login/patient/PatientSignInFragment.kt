package com.syncdev.shifaa.ui.auth.login.patient

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentPatientSignInBinding
import com.syncdev.shifaa.ui.auth.login.SignInFragmentDirections
import com.syncdev.shifaa.ui.patient.PatientActivity


class PatientSignInFragment : Fragment() {



    private lateinit var binding: FragmentPatientSignInBinding

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
                startActivity(Intent(requireContext(), PatientActivity::class.java))
                activity?.finish()
            }
        }

        return binding.root
    }


}