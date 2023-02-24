package com.syncdev.shifaa.ui.auth.forget_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.databinding.FragmentResetPasswordBinding


class ResetPasswordFragment : Fragment() {


    private lateinit var binding: FragmentResetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResetPasswordBinding.inflate(inflater,container,false)

        binding.btnResetPassword.setOnClickListener {
            findNavController().navigate(ResetPasswordFragmentDirections.actionResetPasswordFragmentToSignInFragment())
        }

        binding.buttonBack.setOnClickListener {
            findNavController().navigate(ResetPasswordFragmentDirections.actionResetPasswordFragmentToSignInFragment())
        }

        return binding.root
    }


}