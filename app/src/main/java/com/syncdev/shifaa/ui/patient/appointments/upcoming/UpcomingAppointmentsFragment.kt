package com.syncdev.shifaa.ui.patient.appointments.upcoming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentUpcomingAppointmentsBinding
import com.syncdev.shifaa.ui.patient.appointments.AppointmentsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingAppointmentsFragment : Fragment() {

    private lateinit var binding:FragmentUpcomingAppointmentsBinding
    private lateinit var upcomingAppointmentsAdapter: UpcomingAppointmentsAdapter
    private val appointmentsViewModel by activityViewModels<AppointmentsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_upcoming_appointments,
            container,
            false
        )

        appointmentsViewModel.getAppointmentsByState("Upcoming")

        upcomingAppointmentsAdapter=UpcomingAppointmentsAdapter(requireContext())
        binding.apply {
            rvUpcomingAppointments.adapter=upcomingAppointmentsAdapter
        }

        appointmentsViewModel.upcomingAppointmentsList.observe(viewLifecycleOwner, Observer {
            upcomingAppointmentsAdapter.submitList(it)
            if (it.isEmpty()){
                showNoDataFound(true)
            }else showNoDataFound(false)
        })

        appointmentsViewModel.updateList.observe(viewLifecycleOwner, Observer {update->
            if (update){
                appointmentsViewModel.getAppointmentsByState("Upcoming")
            }
        })

        upcomingAppointmentsAdapter.onCancelClicked = {appointment->
            appointment.id?.let { it -> appointmentsViewModel.cancelAppointmentById(it) }
        }

        return binding.root
    }

    private fun showNoDataFound(show: Boolean){
        binding.apply {
            tvNoUpcomingAppointments.isVisible = show
            ivNoUpcomingAppointments.isVisible = show
        }
    }

}