package com.syncdev.shifaa.ui.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.syncdev.shifaa.databinding.ActivityPatientBinding

class PatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}