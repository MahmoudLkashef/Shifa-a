package com.syncdev.shifaa.ui.patient.appointments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentPatientAppointmentsBinding


class PatientAppointmentsFragment : Fragment() {

    private lateinit var binding:FragmentPatientAppointmentsBinding
    private lateinit var adapter: PatientAppointmentsAdapter
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_patient_appointments,
                container,
                false
        )

        adapter= PatientAppointmentsAdapter(childFragmentManager,lifecycle)


        binding.tlAppointments.addTab(binding.tlAppointments.newTab().setText("Upcoming"))
        binding.tlAppointments.addTab(binding.tlAppointments.newTab().setText("Completed"))
        binding.tlAppointments.addTab(binding.tlAppointments.newTab().setText("Canceled"))

        binding.vpAppointments.adapter=adapter

        binding.tlAppointments.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.vpAppointments.currentItem=tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.vpAppointments.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tlAppointments.selectTab(binding.tlAppointments.getTabAt(position))
            }
        })

        return binding.root
    }


}