package com.syncdev.shifaa.ui.patient.appointments.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentUpcomingAppointmentsBinding
import com.syncdev.shifaa.ui.patient.appointments.AppointmentsViewModel
import com.syncdev.shifaa.ui.patient.appointments.PatientAppointmentsFragmentDirections
import com.syncdev.shifaa.utils.DateUtils
import com.syncdev.shifaa.utils.Dialogs
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
            val dialogs = Dialogs()
            val today = DateUtils.getCurrentDate()
            val canCancel =appointmentsViewModel.validateModifyingAppointment(today,appointment.date)
            if (canCancel){
                dialogs.showCancelUpcomingAppointmentDialog(requireContext()) {
                    appointment.id?.let { it -> appointmentsViewModel.cancelAppointmentById(it) }
                }
            } else {
                dialogs.showCantCancelUpcomingAppointmentDialog(requireContext())
            }
        }

        upcomingAppointmentsAdapter.onRescheduleClicked = {appointment->
            val dialogs = Dialogs()
            val today = DateUtils.getCurrentDate()
            val canReschedule =appointmentsViewModel.validateModifyingAppointment(today,appointment.date)
            if (canReschedule){
                findNavController().navigate(
                    PatientAppointmentsFragmentDirections
                        .actionPatientAppointmentsFragmentToBookAppointmentDetailsFragment(
                            appointment.doctor.id!!,
                            "",
                            "",
                            appointment.id
                        )
                )
            } else{
                dialogs.showCantRescheduleUpcomingAppointmentDialog(requireContext())
            }
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