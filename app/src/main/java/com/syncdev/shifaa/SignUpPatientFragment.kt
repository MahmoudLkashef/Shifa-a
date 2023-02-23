package com.syncdev.shifaa

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.databinding.FragmentSignUpPatientBinding
import com.syncdev.shifaa.utils.DateUtils

class SignUpPatientFragment : Fragment() {


    private lateinit var binding: FragmentSignUpPatientBinding
    private val TAG="SignUpPatientFragment"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpPatientBinding.inflate(inflater,container,false)

        val dayAdapter=ArrayAdapter(requireContext(), R.layout.dropdown_item,DateUtils.get31DayList())
        val month=resources.getStringArray(R.array.month)
        val monthAdapter=ArrayAdapter(requireContext(), R.layout.dropdown_item,month)
        val yearAdapter=ArrayAdapter(requireContext(), R.layout.dropdown_item,DateUtils.getLast100Years())

        binding.dropDownMenuDay.setAdapter(dayAdapter)
        binding.dropDownMenuMonth.setAdapter(monthAdapter)
        binding.dropDownMenuYear.setAdapter(yearAdapter)

        binding.imgBackArrowSignupPatient.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnConfirmSignupPatient.setOnClickListener {
            findNavController().navigate(SignUpPatientFragmentDirections.actionSignUpPatientFragmentToSignInFragment())
        }
        return binding.root
    }

}