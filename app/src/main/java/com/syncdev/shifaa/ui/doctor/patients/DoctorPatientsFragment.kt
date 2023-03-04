package com.syncdev.shifaa.ui.doctor.patients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentDoctorPatientsBinding


class DoctorPatientsFragment : Fragment() {

    private lateinit var binding: FragmentDoctorPatientsBinding
    private lateinit var adapter: DoctorPatientsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDoctorPatientsBinding.inflate(inflater, container, false)

        adapter = DoctorPatientsAdapter(childFragmentManager,lifecycle)

        //adding the tabs to the tabLayout
        binding.tlDoctorPatients.addTab(binding.tlDoctorPatients.newTab().setText("Past"))
        binding.tlDoctorPatients.addTab(binding.tlDoctorPatients.newTab().setText("New"),true)


        binding.vpDoctorPatients.adapter = adapter

        binding.tlDoctorPatients.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    //notifying viewPager with the selected fragment to show
                    binding.vpDoctorPatients.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        binding.vpDoctorPatients.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //notifying viewPager with the selected item
                binding.tlDoctorPatients.selectTab(binding.tlDoctorPatients.getTabAt(position))
            }
        })

        return binding.root
    }


}