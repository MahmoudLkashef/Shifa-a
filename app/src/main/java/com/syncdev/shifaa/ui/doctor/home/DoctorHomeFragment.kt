package com.syncdev.shifaa.ui.doctor.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentDoctorHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DoctorHomeFragment : Fragment() {
    private val TAG = "DoctorHomeFragment"
    private lateinit var binding: FragmentDoctorHomeBinding
    private lateinit var homeAdapter: TodayAppointmentsAdapter
    private val doctorHomeViewModel by viewModels<DoctorHomeViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_doctor_home,
            container,
            false
        )

        homeAdapter = TodayAppointmentsAdapter(requireContext())

        doctorHomeViewModel.getDoctor()

        binding.apply {
            rvTodayAppointment.adapter = homeAdapter
        }

        doctorHomeViewModel.upcomingAppointments.observe(viewLifecycleOwner, Observer {
            homeAdapter.submitList(it)
            if (it.isEmpty()) {
                showNoUpcomingAppointments(true)
            } else showNoUpcomingAppointments(false)
        })

        doctorHomeViewModel.doctor.observe(viewLifecycleOwner, Observer { doctor ->
            if (doctor != null) {
                val fullName = "Dr. ${doctor?.firstName} " + doctor?.lastName
                binding.tvDoctorNameHome.text = fullName
                doctorHomeViewModel.getTodayAppointments()
            }
        })

        homeAdapter.onAppointmentClicked = { appointment ->
                   findNavController().navigate(
                       DoctorHomeFragmentDirections.actionDoctorHomeFragmentToPatientDetailsFragment(
                           appointment.id.toString(),
                           appointment.patient.id.toString(),
                           appointment.doctor.id.toString(),
                           appointment.comment,
                           "${appointment.doctor.firstName} ${appointment.doctor.lastName}",
                           appointment.doctor.email,
                           appointment.doctor.phoneNumber,
                           appointment.doctor.speciality
                       )
                   )
        }

        return binding.root
    }

    private fun showNoUpcomingAppointments(show: Boolean) {
        binding.apply {
            tvNoUpcomingDoctorHome.isVisible = show
            ivNoUpcomingDoctorHome.isVisible = show
            rvTodayAppointment.isVisible=!show
        }
    }

}