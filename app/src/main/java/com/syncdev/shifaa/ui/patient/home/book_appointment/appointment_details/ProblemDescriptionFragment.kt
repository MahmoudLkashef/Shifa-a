package com.syncdev.shifaa.ui.patient.home.book_appointment.appointment_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentProblemDescriptionBinding


class ProblemDescriptionFragment : Fragment() {

    private lateinit var binding: FragmentProblemDescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProblemDescriptionBinding.inflate(
            layoutInflater,
            container,
            false
        )



        return binding.root
    }

}