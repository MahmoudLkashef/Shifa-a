package com.syncdev.shifaa.ui.doctor.patients.past_patients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentDoctorPastPatientsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DoctorPastPatientsFragment : Fragment() {

    private lateinit var binding: FragmentDoctorPastPatientsBinding
    private lateinit var adapter: DoctorPastPatientsAdapter
    private val pastPatientsViewModel by viewModels<DoctorPastPatientsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_doctor_past_patients,
            container,
            false
        )

        adapter = DoctorPastPatientsAdapter(requireContext())

        binding.apply {
            rvPastPatients.layoutManager = GridLayoutManager(requireContext(),2)
            rvPastPatients.adapter = adapter
        }

        pastPatientsViewModel.getCompletedAppointments()

        pastPatientsViewModel.completedAppointments.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            val numOfPatients = "${it.size} Previous patients"
            binding.tvPastPatientsNumber.text = numOfPatients
            if (it.isEmpty()){
                showNoOldPatients(true)
            } else showNoOldPatients(false)
        })



        return binding.root
    }

    private fun showNoOldPatients(show: Boolean){
        binding.apply {
            ivNoOldPatients.isVisible = show
            tvNoOldPatients.isVisible = show
        }
    }

}