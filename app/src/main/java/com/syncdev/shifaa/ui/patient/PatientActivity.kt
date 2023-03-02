package com.syncdev.shifaa.ui.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.ActivityPatientBinding

class PatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_patient) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavPatient.setupWithNavController(navController)
    }
}