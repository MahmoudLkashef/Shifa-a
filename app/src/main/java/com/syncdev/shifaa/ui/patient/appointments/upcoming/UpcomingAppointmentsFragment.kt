package com.syncdev.shifaa.ui.patient.appointments.upcoming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.syncdev.domain.model.SchedulePatient
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentUpcomingAppointmentsBinding

class UpcomingAppointmentsFragment : Fragment() {

    private lateinit var binding:FragmentUpcomingAppointmentsBinding
    private lateinit var upcomingAppointmentsAdapter: UpcomingAppointmentsAdapter
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
        val doctors= listOf<SchedulePatient>(
            SchedulePatient(1,"Farah Nader","completed","10 Jan 2023","10:30 AM"),
            SchedulePatient(2,"Menna Ahmed","completed","10 Jan 2023","10:30 AM"),
            SchedulePatient(3,"Layla Hasaan","completed","10 Jan 2023","10:30 AM"),
            SchedulePatient(4,"Rana Reda","completed","10 Jan 2023","10:30 AM"),
        )

        upcomingAppointmentsAdapter=UpcomingAppointmentsAdapter()
        upcomingAppointmentsAdapter.submitList(doctors)
        binding.rvUpcomingAppointments.adapter=upcomingAppointmentsAdapter

        return binding.root
    }

}