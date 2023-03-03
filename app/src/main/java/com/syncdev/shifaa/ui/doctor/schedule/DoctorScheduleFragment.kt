package com.syncdev.shifaa.ui.doctor.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.syncdev.domain.model.Calendar
import com.syncdev.domain.model.SchedulePatient
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentDoctorScheduleBinding
import com.syncdev.shifaa.utils.DateUtils


class DoctorScheduleFragment : Fragment() {
    private lateinit var binding: FragmentDoctorScheduleBinding
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var rescheduleAdapter: RescheduleAdapter
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
        val dateList = listOf<Calendar>(
            Calendar(1, "01", "Sat"),
            Calendar(2, "02", "Sun"),
            Calendar(3, "03", "Mon"),
            Calendar(4, "04", "Thu"),
            Calendar(5, "05", "Wen"),
            Calendar(6, "06", "Thur")
        )

        val patients= listOf<SchedulePatient>(
            SchedulePatient(1,"Karma Mohamed","Follow Up","10 Jan 2023","10:30 AM"),
            SchedulePatient(2,"Ola Ibrahim","Follow Up","10 Jan 2023","10:30 AM"),
            SchedulePatient(3,"Asmaa hassan","Follow Up","10 Jan 2023","10:30 AM"),
            SchedulePatient(4,"Layla Mahmoud","Follow Up","10 Jan 2023","10:30 AM"),
        )

        calendarAdapter = CalendarAdapter()
        rescheduleAdapter=RescheduleAdapter()

        binding.rvCalendar.adapter = calendarAdapter
        binding.rvReschedulePatient.adapter=rescheduleAdapter

        calendarAdapter.submitList(dateList)
        rescheduleAdapter.submitList(patients)

        return binding.root
    }


}