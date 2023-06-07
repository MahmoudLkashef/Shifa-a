package com.syncdev.shifaa.ui.auth.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.databinding.FragmentSplashBinding
import com.syncdev.shifaa.ui.doctor.DoctorActivity
import com.syncdev.shifaa.ui.patient.PatientActivity
import com.syncdev.shifaa.utils.Constants
import com.syncdev.shifaa.utils.OnBoardingConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater,container,false)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2500)
            if (onBoardingFinished()){
                when(checkUserType()){
                    Constants.PATIENT ->{
                        startActivity(Intent(requireContext(), PatientActivity::class.java))
                        activity?.finish()
                    }
                    Constants.DOCTOR ->{
                        startActivity(Intent(requireContext(), DoctorActivity::class.java))
                        activity?.finish()
                    }
                    "none" -> findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSignInFragment())
                }
            }else{
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToViewPagerFragment())
            }
        }


        return binding.root
    }

    private fun onBoardingFinished(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences(OnBoardingConstants.ON_BOARDING_STATE,Context.MODE_PRIVATE)
        return sharedPref.getBoolean(OnBoardingConstants.ON_BOARDING_FINISHED,false)
    }

    private fun checkUserType(): String {
        // Obtain SharedPreferences instance
        val sharedPreferences =
            requireContext().getSharedPreferences(Constants.NAVIGATION_STATE, Context.MODE_PRIVATE)

        // Retrieve the user type from SharedPreferences

        return when (sharedPreferences.getString(Constants.USER, null)) {
            Constants.DOCTOR -> Constants.DOCTOR
            Constants.PATIENT ->Constants.PATIENT
            else -> "none"
        }
    }


}