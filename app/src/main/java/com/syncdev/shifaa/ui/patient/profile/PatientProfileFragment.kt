package com.syncdev.shifaa.ui.patient.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentPatientProfileBinding
import com.syncdev.shifaa.ui.auth.MainActivity
import com.syncdev.shifaa.utils.Dialogs
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PatientProfileFragment : Fragment() {

    private val TAG = "PatientProfileFragment"
    private lateinit var binding: FragmentPatientProfileBinding
    private val patientProfileViewModel by viewModels<PatientProfileViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_patient_profile,
            container,
            false
        )
        //get the cached patient object to view its information
        patientProfileViewModel.getPatientData()

        binding.apply {
            viewModel = patientProfileViewModel

            cvLogoutPatient.setOnClickListener {
                Dialogs().showSignOutDialog(requireContext()) {
                    patientProfileViewModel.signOut()
                }
            }

            cvEditProfilePatient.setOnClickListener {
                findNavController().navigate(PatientProfileFragmentDirections.actionPatientProfileFragmentToPatientEditProfileFragment())
            }
        }

        patientProfileViewModel.signedOut.observe(viewLifecycleOwner, Observer { signedOut ->
            if (signedOut) {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                activity?.finish()
            }
        })

        return binding.root
    }


}