package com.syncdev.shifaa.ui.auth.forget_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.databinding.FragmentOtpVerificationBinding

class OtpVerificationFragment : Fragment() {


    private lateinit var binding: FragmentOtpVerificationBinding
    private val TAG = "OtpVerificationFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOtpVerificationBinding.inflate(inflater,container,false)

        binding.btnVerify.setOnClickListener {
            findNavController().navigate(OtpVerificationFragmentDirections.actionOtpVerificationFragmentToResetPasswordFragment())
        }

        binding.buttonBackOtp.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

}