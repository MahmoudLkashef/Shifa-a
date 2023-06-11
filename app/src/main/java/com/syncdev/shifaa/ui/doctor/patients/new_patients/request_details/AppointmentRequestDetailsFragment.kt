package com.syncdev.shifaa.ui.doctor.patients.new_patients.request_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.syncdev.domain.model.Appointment
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentAppointmentRequestDetailsBinding
import com.syncdev.shifaa.utils.Internet
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AppointmentRequestDetailsFragment : Fragment() {

    private val args: AppointmentRequestDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentAppointmentRequestDetailsBinding
    private val appointmentRequestViewModel by viewModels<AppointmentRequestDetailsViewModel>()

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
        val patientId = args.patientId
        val patientGender = args.patientGender
        val time = args.time
        val date = args.date
        val comment = args.comment

        appointmentRequestViewModel.getPatient(patientId)

        binding.apply {
            tvPtientNameAppointmentRequest.text = patientName
            tvCommentsDetailsAppointmentRequest.text = comment
            tvAppointmentRequestTimeDetails.text = time
            tvAppointmentRequestDateDetails.text = date
            when(patientGender){
                "Male" -> {
                    ivPatientPicAppointmentRequest
                        .setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.patient_male))
                }
                "Female" -> {
                    ivPatientPicAppointmentRequest
                        .setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.patient_female))
                }
            }
            btnAcceptAppointmentRequest.setOnClickListener {
                if (Internet.isInternetConnected(requireContext())){
                    val appointment = appointmentRequestViewModel.gatherDataForAppointment(comment,time, date)
                    appointmentRequestViewModel.createNewAppointment(appointment)
                }else{
                    Snackbar.make(requireView(),"Please Check Your Internet Connection",Snackbar.ANIMATION_MODE_SLIDE)
                        .show()
                }
            }
            btnDeclineAppointmentRequest.setOnClickListener {
                appointmentRequestViewModel.getDoctor().id?.let { id ->
                    appointmentRequestViewModel.deleteAppointmentRequest(
                        doctorId = id,
                        date = date,
                        time = time
                    )
                }
            }
            ivBackAppointmentRequest.setOnClickListener {
                navigateBack()
            }
        }

        appointmentRequestViewModel.navigate.observe(viewLifecycleOwner, Observer {
            findNavController().popBackStack()
        })

        appointmentRequestViewModel.saved.observe(viewLifecycleOwner, Observer { saved->
            if (saved){
                appointmentRequestViewModel.deleteAppointmentRequest(
                    appointmentRequestViewModel.getDoctor().id!!,
                    date,
                    time
                )
            }
        })

        return binding.root
    }

    private fun navigateBack(){
        findNavController().popBackStack()
    }

}