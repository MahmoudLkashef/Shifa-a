package com.syncdev.shifaa.ui.patient.appointments.canceled

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentCanceledAppointmentsBinding
import com.syncdev.shifaa.ui.patient.appointments.AppointmentsViewModel

class CanceledAppointmentsFragment : Fragment() {

    private lateinit var binding:FragmentCanceledAppointmentsBinding
    private lateinit var canceledAppointmentsAdapter: CanceledAppointmentsAdapter
    private val appointmentsViewModel by activityViewModels<AppointmentsViewModel>()

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

        appointmentsViewModel.getAppointmentsByState("Canceled")

        canceledAppointmentsAdapter= CanceledAppointmentsAdapter(requireContext())

        binding.apply {
            rvCanceledAppointments.adapter=canceledAppointmentsAdapter
        }

        appointmentsViewModel.canceledAppointmentsList.observe(viewLifecycleOwner, Observer {
            canceledAppointmentsAdapter.submitList(it)
            if (it.isEmpty()){
                showNoDataFound(true)
            }else showNoDataFound(false)
        })

        return binding.root
    }

    private fun showNoDataFound(show: Boolean){
        binding.apply {
            ivNoCanceledAppointments.isVisible = show
            tvNoCanceledAppointments.isVisible = show
        }
    }

}