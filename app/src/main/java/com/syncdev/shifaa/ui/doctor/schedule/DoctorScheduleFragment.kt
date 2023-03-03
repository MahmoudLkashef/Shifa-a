package com.syncdev.shifaa.ui.doctor.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentDoctorScheduleBinding


class DoctorScheduleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_schedule, container, false)
    }


}