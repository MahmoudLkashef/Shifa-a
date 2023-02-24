package com.syncdev.shifaa.ui.auth.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.databinding.FragmentOnBoardingThirdBinding
import com.syncdev.shifaa.utils.OnBoardingConstants
import com.syncdev.shifaa.ui.auth.onboarding.ViewPagerFragmentDirections


class OnBoardingThirdFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingThirdBinding.inflate(inflater,container,false)

        binding.btnOnboardingThirdNext.setOnClickListener {
            findNavController().navigate(ViewPagerFragmentDirections.actionViewPagerFragmentToSignInFragment())
            onBoardingFinish()
        }

        return binding.root
    }

    private fun onBoardingFinish(){
        val sharedPref = requireActivity().getSharedPreferences(OnBoardingConstants.ON_BOARDING_STATE,Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(OnBoardingConstants.ON_BOARDING_FINISHED,true)
        editor.apply()
    }

}