package com.syncdev.shifaa.ui.patient.reports

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentPatientReportsBinding


class PatientReportsFragment : Fragment() {
    private val TAG="PatientReportsFragment"
    private lateinit var binding:FragmentPatientReportsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_patient_reports,
            container,
            false
        )

        return binding.root
    }


}