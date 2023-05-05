package com.syncdev.shifaa.ui.patient.home.book_appointment.appointment_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentSelectAppointmentDateTimeBinding


class SelectAppointmentDateTimeFragment : Fragment() {


    private lateinit var binding: FragmentSelectAppointmentDateTimeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_select_appointment_date_time,
            container,
            false
        )

        //TODO set the minDate for the calender view
        //TODO add chips programmatically

        return binding.root
    }


}