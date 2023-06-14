package com.syncdev.shifaa.ui.patient.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentPatientHomeBinding
import com.syncdev.shifaa.ui.patient.home.upcoming_appointments.UpcomingAppointmentsHomeViewModel
import com.syncdev.shifaa.ui.patient.home.upcoming_appointments.UpcomingAppointmentsHomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientHomeFragment : Fragment() {

    private val TAG="PatientHomeFragment"
    private lateinit var binding: FragmentPatientHomeBinding
    private lateinit var upcomingAppointmentsAdapter: UpcomingAppointmentsHomeAdapter
    private val upcomingAppointmentsViewModel by viewModels<UpcomingAppointmentsHomeViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_patient_home,
            container,
            false
        )

        upcomingAppointmentsViewModel.getUpcomingAppointments()

        upcomingAppointmentsAdapter = UpcomingAppointmentsHomeAdapter()

        upcomingAppointmentsViewModel.upcomingAppointments.observe(
            viewLifecycleOwner,
            Observer { appointments ->
                Log.i(TAG, "onCreateView: $appointments")
                if(appointments.isNotEmpty()){
                    showUpcomingAppointmentsRecycleView()
                    upcomingAppointmentsAdapter.submitList(appointments)
                }
                else showUpcomingAppointmentsEmptyState()
            })

        binding.apply {

            binding.rvUpcomingAppointments.adapter = upcomingAppointmentsAdapter

            viewFindDoctorPatientHome.setOnClickListener {
                findNavController().navigate(PatientHomeFragmentDirections.actionPatientHomeFragmentToFindDoctorFragment())
            }

            viewMedicalCardPatientHome.setOnClickListener {
                findNavController().navigate(PatientHomeFragmentDirections.actionPatientHomeFragmentToMedicalCardFragment())
            }
        }

        return binding.root
    }

    private fun showUpcomingAppointmentsRecycleView() {
        binding.groupEmptyState.visibility = View.INVISIBLE
        binding.rvUpcomingAppointments.visibility = View.VISIBLE
    }
    private fun showUpcomingAppointmentsEmptyState() {
        binding.groupEmptyState.visibility = View.VISIBLE
        binding.rvUpcomingAppointments.visibility = View.INVISIBLE
    }

}