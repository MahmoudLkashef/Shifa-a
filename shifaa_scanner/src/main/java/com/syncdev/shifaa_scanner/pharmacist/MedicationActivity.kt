package com.syncdev.shifaa_scanner.pharmacist

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.syncdev.shifaa_scanner.databinding.ActivityMedicationBinding
import com.syncdev.shifaa_scanner.model.Medication

class MedicationActivity : AppCompatActivity() {

    private val TAG="MedicationActivity"
    private lateinit var binding:ActivityMedicationBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMedicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val medicationAdapter=MedicationAdapter()

        val medications = intent.getParcelableArrayListExtra<Medication>("Medications")

        medicationAdapter.submitList(medications?.toList())

        binding.apply {
            rvMedication.adapter=medicationAdapter
        }


    }
}