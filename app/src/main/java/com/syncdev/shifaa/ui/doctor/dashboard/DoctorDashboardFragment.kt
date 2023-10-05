package com.syncdev.shifaa.ui.doctor.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentDoctorDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorDashboardFragment : Fragment() {

    private lateinit var binding: FragmentDoctorDashboardBinding
    private val doctorDashboardViewModel by viewModels<DoctorDashboardViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_doctor_dashboard,
            container,
            false
        )

        val todaysAppointmentsAdapter = TodaysAppointmentsAdapter(requireContext())
        binding.rvTodaysPatientDashboard.adapter = todaysAppointmentsAdapter

        doctorDashboardViewModel.getTodayAppointments()
        doctorDashboardViewModel.getAppointmentsRequests()
        doctorDashboardViewModel.getCompletedAppointments()

        doctorDashboardViewModel.upcomingAppointments.observe(
            viewLifecycleOwner,
            Observer { appointments ->
                if (appointments.isNotEmpty()) {
                    todaysAppointmentsAdapter.submitList(appointments)
                    showEmptyState(false)
                }
                else showEmptyState(true)
            })

        doctorDashboardViewModel.appointmentsRequests.observe(
            viewLifecycleOwner,
            Observer { appointmentsRequests ->
                if (appointmentsRequests.isNotEmpty()) {
                    binding.tvPendingAppointmentsDashboard.text =
                        appointmentsRequests.size.toString()
                }
            })

        doctorDashboardViewModel.completedAppointments.observe(
            viewLifecycleOwner,
            Observer { completedAppointments ->
                if (completedAppointments.isNotEmpty()) {
                    binding.apply {
                        tvTotalPatientThisYear.text =
                            completedAppointments.size.toString() + " Patients"
                        tvCompletedAppointmentsDashboard.text =
                            completedAppointments.size.toString()
                        tvTotalPatientsThisMonth.text = completedAppointments.size.toString()
                        val rating = getRate(
                            completedAppointments.first().doctor.totalRating,
                            completedAppointments.first().doctor.numOfReviews
                        )
                        tvDoctorTotalStartsDashboard.text =rating
                    }
                }
            })

        return binding.root
    }

    private fun getRate(totalRating: Float, numOfReviews: Int): String {
        val rate = totalRating / numOfReviews
        return String.format("%.1f", rate)
    }

    fun showEmptyState(show: Boolean){
        binding.apply {
            imgEmptyTodaysAppointmentState.isVisible=show
            tvEmptyTodaysAppointmentsState.isVisible=show
        }
    }

}