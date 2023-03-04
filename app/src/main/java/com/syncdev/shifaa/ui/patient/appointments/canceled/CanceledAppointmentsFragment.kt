package com.syncdev.shifaa.ui.patient.appointments.canceled

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.syncdev.domain.model.SchedulePatient
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentCanceledAppointmentsBinding

class CanceledAppointmentsFragment : Fragment() {

    private lateinit var binding:FragmentCanceledAppointmentsBinding
    private lateinit var canceledAppointmentsAdapter: CanceledAppointmentsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_canceled_appointments,
            container,
            false
        )

        val doctors= listOf<SchedulePatient>(
            SchedulePatient(1,"Ahmed Nader","canceled","10 Jan 2023","10:30 AM"),
            SchedulePatient(2,"Abdelfattah Wazeer","canceled","10 Jan 2023","10:30 AM"),
            SchedulePatient(3,"Mohamed Hasaan","canceled","10 Jan 2023","10:30 AM"),
            SchedulePatient(4,"Mahmoud Reda","canceled","10 Jan 2023","10:30 AM"),
        )

        canceledAppointmentsAdapter= CanceledAppointmentsAdapter()
        canceledAppointmentsAdapter.submitList(doctors)
        binding.rvCanceledAppointments.adapter=canceledAppointmentsAdapter

        return binding.root
    }

}