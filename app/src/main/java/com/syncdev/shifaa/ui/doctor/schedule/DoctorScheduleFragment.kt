package com.syncdev.shifaa.ui.doctor.schedule

import CalendarAdapter
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
import com.syncdev.shifaa.databinding.FragmentDoctorScheduleBinding
import com.syncdev.shifaa.utils.DateUtils
import com.syncdev.shifaa.utils.Dialogs
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DoctorScheduleFragment : Fragment() {
    private lateinit var binding: FragmentDoctorScheduleBinding
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var rescheduleAdapter: RescheduleAdapter
    private val doctorScheduleViewModel by viewModels<DoctorScheduleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_doctor_schedule,
            container,
            false
        )


        doctorScheduleViewModel.getAppointmentsByDoctorId()

        calendarAdapter = CalendarAdapter()
        rescheduleAdapter = RescheduleAdapter(requireContext())

        binding.apply {
            rvCalendar.adapter = calendarAdapter
            rvReschedulePatient.adapter=rescheduleAdapter
        }

        doctorScheduleViewModel.getAvailableDaysList()

        calendarAdapter.submitList(doctorScheduleViewModel.availableDays.value)


        calendarAdapter.onDateClicked = {
            doctorScheduleViewModel.chosenDate.value = it.date
            doctorScheduleViewModel.getAppointmentsByDate(it.date)
        }

        rescheduleAdapter.onSeeMoreClicked = { appointment ->
            val patientName = "${appointment.patient.firstName} " + appointment.patient.lastName
            findNavController().navigate(
                DoctorScheduleFragmentDirections.actionDoctorScheduleFragmentToAppointmentRequestDetailsFragment(
                    patientName = patientName,
                    patientId = appointment.patient.id!!,
                    patientGender = appointment.patient.gender,
                    time = appointment.time,
                    date = appointment.date,
                    comment = appointment.comment,
                    request = false
                )
            )
        }

        rescheduleAdapter.onCancelClicked = {appointment ->
            val dialogs = Dialogs()
            val today = DateUtils.getCurrentDate()
            val canReschedule =doctorScheduleViewModel.validateModifyingAppointment(today,appointment.date)
            if (canReschedule){
                dialogs.showCancelUpcomingAppointmentDialog(requireContext()){
                    doctorScheduleViewModel.cancelAppointmentById(appointment.id!!)
                }
            }else{
                dialogs.showCantCancelUpcomingAppointmentDialog(requireContext())
            }
        }

        doctorScheduleViewModel.updateList.observe(viewLifecycleOwner, Observer {update->
            if (update){
                doctorScheduleViewModel.getAppointmentsByDoctorId()
            }
        })

        doctorScheduleViewModel.filteredAppointments.observe(viewLifecycleOwner, Observer {appointments->
            rescheduleAdapter.submitList(appointments)
        })




        return binding.root
    }


}