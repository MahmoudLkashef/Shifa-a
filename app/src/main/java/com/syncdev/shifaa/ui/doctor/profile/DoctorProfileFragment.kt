package com.syncdev.shifaa.ui.doctor.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentDoctorProfileBinding
import com.syncdev.shifaa.ui.auth.MainActivity
import com.syncdev.shifaa.utils.Dialogs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorProfileFragment : Fragment() {

    private lateinit var binding: FragmentDoctorProfileBinding
    private val TAG = "DoctorProfileFragment"
    private val doctorProfileViewModel by viewModels<DoctorProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_doctor_profile,
            container,
            false
        )

        doctorProfileViewModel.getDoctorData()

        binding.apply {
            viewModel = doctorProfileViewModel

            cvLogoutDoctor.setOnClickListener {
                Dialogs().showSignOutDialog(requireContext()) {
                    doctorProfileViewModel.signOut()
                }
            }

            cvEditProfileDoctor.setOnClickListener {
                findNavController().navigate(DoctorProfileFragmentDirections
                    .actionDoctorProfileFragmentToDoctorEditProfileFragment())
            }
        }

        doctorProfileViewModel.signedOut.observe(viewLifecycleOwner, Observer { signedOut ->
            if (signedOut) {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                activity?.finish()
            }
        })

        return binding.root
    }


}