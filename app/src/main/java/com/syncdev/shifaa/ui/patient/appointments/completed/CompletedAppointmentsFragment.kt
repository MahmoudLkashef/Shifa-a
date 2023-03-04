package com.syncdev.shifaa.ui.patient.appointments.completed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.syncdev.domain.model.SchedulePatient
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentCompletedAppointmentsBinding

class CompletedAppointmentsFragment : Fragment() {

    private lateinit var binding:FragmentCompletedAppointmentsBinding
    private lateinit var completedAppointmentsAdapter: CompletedAppointmentsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_completed_appointments,
            container,
            false
        )
        val doctors= listOf<SchedulePatient>(
            SchedulePatient(1,"Farah Nader","completed","10 Jan 2023","10:30 AM"),
            SchedulePatient(2,"Menna Ahmed","completed","10 Jan 2023","10:30 AM"),
            SchedulePatient(3,"Layla Hasaan","completed","10 Jan 2023","10:30 AM"),
            SchedulePatient(4,"Rana Reda","completed","10 Jan 2023","10:30 AM"),
        )

        completedAppointmentsAdapter= CompletedAppointmentsAdapter()
        completedAppointmentsAdapter.submitList(doctors)
        binding.tvCompletedAppointments.adapter=completedAppointmentsAdapter

        return binding.root
    }

}