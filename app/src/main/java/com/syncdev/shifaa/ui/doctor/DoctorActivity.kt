package com.syncdev.shifaa.ui.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.ActivityDoctorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoctorBinding
    private val doctorActivityViewModel by viewModels<DoctorActivityViewModel>()
    private val TAG = "DoctorActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_doctor) as NavHostFragment
        val navController = navHostFragment.navController

        //Get the firebase user from shared preferences and assign it to UserData.user
        //So we can use it later
        val doctorId = doctorActivityViewModel.getDoctorId()
        Log.i(TAG, "onCreate: $doctorId")
        if (doctorId != null) {
            doctorActivityViewModel.searchDoctorById(doctorId)
        }

        binding.bottomNavDoctor.setupWithNavController(navController)

        binding.bottomNavDoctor.setOnItemSelectedListener { item ->
            // When an item in the bottom navigation view is selected, this listener is triggered

            // Navigate to the associated destination based on the selected item
            NavigationUI.onNavDestinationSelected(item, navController)

            // Return true to indicate that the selection event has been handled
            // This ensures that the selected item remains visually highlighted
            return@setOnItemSelectedListener true
        }
    }
}