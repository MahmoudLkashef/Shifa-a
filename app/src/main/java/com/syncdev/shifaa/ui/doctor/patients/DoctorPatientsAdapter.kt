package com.syncdev.shifaa.ui.doctor.patients

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.syncdev.shifaa.ui.doctor.patients.new_patients.DoctorNewPatientsFragment
import com.syncdev.shifaa.ui.doctor.patients.past_patients.DoctorPastPatientsFragment

class DoctorPatientsAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0){
            DoctorPastPatientsFragment()
        }else{
            DoctorNewPatientsFragment()
        }
    }

}