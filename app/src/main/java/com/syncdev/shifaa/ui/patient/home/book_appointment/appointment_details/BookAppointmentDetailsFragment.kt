package com.syncdev.shifaa.ui.patient.home.book_appointment.appointment_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentBookAppointmentDetailsBinding


class BookAppointmentDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBookAppointmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_book_appointment_details,
            container,
            false
        )

        binding.btnBookNowBookAppointmentDetails.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivBackBookAppointmentDetails.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }


}