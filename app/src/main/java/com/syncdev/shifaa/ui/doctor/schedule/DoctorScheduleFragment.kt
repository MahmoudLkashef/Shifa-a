package com.syncdev.shifaa.ui.doctor.schedule

import CalendarAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.syncdev.domain.model.CalendarModel
import com.syncdev.domain.model.SchedulePatient
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentDoctorScheduleBinding
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

        //TODO get a list of appointments here

        val patients= listOf<SchedulePatient>(
            SchedulePatient(1,"Karma Mohamed","Follow Up","10 Jan 2023","10:30 AM"),
            SchedulePatient(2,"Ola Ibrahim","Follow Up","10 Jan 2023","10:30 AM"),
            SchedulePatient(3,"Asmaa hassan","Follow Up","10 Jan 2023","10:30 AM"),
            SchedulePatient(4,"Layla Mahmoud","Follow Up","10 Jan 2023","10:30 AM"),
        )

        calendarAdapter = CalendarAdapter()
        rescheduleAdapter = RescheduleAdapter()

        binding.apply {
            rvCalendar.adapter = calendarAdapter
            rvReschedulePatient.adapter=rescheduleAdapter
        }

        doctorScheduleViewModel.getAvailableDaysList()

        calendarAdapter.submitList(doctorScheduleViewModel.availableDays.value)
        rescheduleAdapter.submitList(patients)

        return binding.root
    }


}