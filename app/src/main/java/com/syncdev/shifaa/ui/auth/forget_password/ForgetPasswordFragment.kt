package com.syncdev.shifaa.ui.auth.forget_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.databinding.FragmentForgetPasswordBinding


class ForgetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForgetPasswordBinding.inflate(inflater,container,false)

        binding.btnContinue.setOnClickListener {
            findNavController().navigate(ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToOtpVerificationFragment())
        }

        binding.buttonBackForgot.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

}