package com.syncdev.shifaa.ui.auth.forget_password

import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentSendOtpBinding

class SendOtpFragment : Fragment() {

    private val TAG = "SendOtpFragment"
    private lateinit var binding: FragmentSendOtpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_send_otp, container, false)

        val selectedMethod = arguments?.getString("otpMethod")
        val role = arguments?.getString("role")
        Log.i(TAG, "selected method : $selectedMethod")
        Log.i(TAG, "selected method : $role")

        with(binding) {
            tvOtpMethod.text = selectedMethod
            etOtpMethod.hint = selectedMethod

            when(selectedMethod){
                "Phone"->etOtpMethod.inputType=InputType.TYPE_CLASS_PHONE
            }

            btnBackSendOtp.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSendOtp.setOnClickListener {
                //todo validate and send otp method input to viewModel
                findNavController().navigate(SendOtpFragmentDirections.actionSendOtpFragmentToOtpVerificationFragment())
            }
        }


        return binding.root
    }

}