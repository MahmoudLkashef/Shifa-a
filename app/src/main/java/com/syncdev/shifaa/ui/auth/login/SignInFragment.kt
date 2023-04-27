package com.syncdev.shifaa.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.syncdev.shifaa.databinding.FragmentSignInBinding
import com.syncdev.shifaa.ui.auth.login.doctor.DoctorSignInFragment
import com.syncdev.shifaa.ui.auth.login.patient.PatientSignInFragment
import com.syncdev.shifaa.ui.auth.onboarding.ViewPagerAdapter
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

        val fragmentList = arrayListOf<Fragment>(
            PatientSignInFragment(),
            DoctorSignInFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPagerSignin.adapter = adapter

        return binding.root
    }


}