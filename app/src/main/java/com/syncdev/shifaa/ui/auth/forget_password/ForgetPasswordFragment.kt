package com.syncdev.shifaa.ui.auth.forget_password

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import com.syncdev.shifaa.databinding.FragmentForgetPasswordBinding


class ForgetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgetPasswordBinding
    private val TAG = "ForgetPasswordFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForgetPasswordBinding.inflate(inflater,container,false)

        with(binding){
            btnContinue.isVisible = false
            cardViewPhone.isChecked = false
            cardViewEmail.isChecked = false
            cardViewPhone.setOnClickListener {
                setChecked(cardViewPhone,cardViewEmail)
            }

            cardViewEmail.setOnClickListener {
                setChecked(cardViewEmail,cardViewPhone)
            }

            buttonBackForgot.setOnClickListener {
                findNavController().popBackStack()
            }

            btnContinue.setOnClickListener {
                if (validateSelectedMethod()) {
                    val selectedMethod: String = if (cardViewEmail.isChecked) {
                        "Email"
                    } else {
                        "Phone"
                    }
                    Log.i(TAG, "continue using : $selectedMethod")
                    findNavController().navigate(
                        ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToOtpVerificationFragment(selectedMethod)
                    )
                }
            }
        }

        return binding.root
    }

    private fun validateSelectedMethod(): Boolean{
        return (binding.cardViewEmail.isChecked || binding.cardViewPhone.isChecked)
    }

    private fun setChecked(selected: MaterialCardView, unselected: MaterialCardView){
        selected.isChecked = true
        unselected.isChecked = false
        binding.btnContinue.isVisible = true
    }

}