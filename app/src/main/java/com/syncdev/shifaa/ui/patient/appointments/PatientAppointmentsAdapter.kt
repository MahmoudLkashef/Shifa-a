package com.syncdev.shifaa.ui.patient.appointments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.syncdev.shifaa.ui.patient.appointments.canceled.CanceledAppointmentsFragment
import com.syncdev.shifaa.ui.patient.appointments.completed.CompletedAppointmentsFragment
import com.syncdev.shifaa.ui.patient.appointments.upcoming.UpcomingAppointmentsFragment

class PatientAppointmentsAdapter(fragmentManager: FragmentManager,lifecycle:Lifecycle)
    :FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->UpcomingAppointmentsFragment()
            1->CompletedAppointmentsFragment()
            else->CanceledAppointmentsFragment()
        }
    }

}