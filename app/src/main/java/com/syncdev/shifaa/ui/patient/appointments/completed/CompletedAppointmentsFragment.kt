package com.syncdev.shifaa.ui.patient.appointments.completed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentCompletedAppointmentsBinding
import com.syncdev.shifaa.ui.patient.appointments.AppointmentsViewModel
import com.syncdev.shifaa.utils.Dialogs

class CompletedAppointmentsFragment : Fragment() {

    private lateinit var binding:FragmentCompletedAppointmentsBinding
    private lateinit var completedAppointmentsAdapter: CompletedAppointmentsAdapter
    private val appointmentsViewModel by activityViewModels<AppointmentsViewModel>()
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

        appointmentsViewModel.getAppointmentsByState("Completed")

        completedAppointmentsAdapter= CompletedAppointmentsAdapter(requireContext())

        binding.apply {
            tvCompletedAppointments.adapter=completedAppointmentsAdapter
        }

        completedAppointmentsAdapter.onLeaveAReviewClicked = {
            Dialogs().showRateDoctorDialog(requireContext()){
                Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_SHORT).show()
            }
        }

        appointmentsViewModel.completedAppointmentsList.observe(viewLifecycleOwner, Observer {
            completedAppointmentsAdapter.submitList(it)
            if (it.isEmpty()){
                showNoDataFound(true)
            }else showNoDataFound(false)
        })

        return binding.root
    }

    private fun showNoDataFound(show: Boolean){
        binding.apply {
            tvNoCompletedAppointments.isVisible = show
            ivNoCompletedAppointments.isVisible = show
        }
    }

}