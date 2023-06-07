package com.syncdev.shifaa.ui.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.ActivityPatientBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientBinding
    private val patientActivityViewModel by viewModels<PatientActivityViewModel>()
    private val TAG = "PatientActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_patient) as NavHostFragment
        val navController = navHostFragment.navController

        //Get the firebase user from shared preferences and assign it to UserData.user
        //So we can use it later
        val patientId = patientActivityViewModel.getPatientId()
        Log.i(TAG, "onCreate: $patientId")
        if (patientId != null) {
            patientActivityViewModel.searchPatientById(patientId)
        }

        binding.bottomNavPatient.setupWithNavController(navController)
    }
}