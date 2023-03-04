package com.syncdev.shifaa.ui.doctor.patients.new_patients.request_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentAppointmentRequestDetailsBinding


class AppointmentRequestDetailsFragment : Fragment() {

    private val args: AppointmentRequestDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentAppointmentRequestDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_appointment_request_details,
            container,
            false
        )

        val patientName = args.patientName

        binding.tvPtientNameAppointmentRequest.text = patientName
        binding.btnAcceptAppointmentRequest.setOnClickListener {
            navigateBack()
        }
        binding.btnDeclineAppointmentRequest.setOnClickListener {
            navigateBack()
        }
        binding.ivBackAppointmentRequest.setOnClickListener {
            navigateBack()
        }


        return binding.root
    }

    private fun navigateBack(){
        findNavController().popBackStack()
    }

}