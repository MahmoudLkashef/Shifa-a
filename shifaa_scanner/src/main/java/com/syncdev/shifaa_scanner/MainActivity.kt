package com.syncdev.shifaa_scanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.syncdev.shifaa_scanner.databinding.ActivityMainBinding
import com.syncdev.shifaa_scanner.model.Medication
import com.syncdev.shifaa_scanner.pharmacist.MedicationActivity

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
            if (result.contents != null) {
                val (type, medications) = deserializeMedicines(result.contents)
                if(type=="Medications"){
                    val intent = Intent(this, MedicationActivity::class.java)
                    val medicationList = ArrayList(medications)
                    intent.putParcelableArrayListExtra("Medications",medicationList)
                    startActivity(intent)
                }
            }
        }

        binding.btnScan.setOnClickListener {
            scannerOptions(barcodeLauncher)
        }
    }

    private fun scannerOptions(barcodeLauncher: ActivityResultLauncher<ScanOptions>) {
        val option = ScanOptions()
        option.setPrompt("Scan a barcode")
        option.setOrientationLocked(true)
        option.setBeepEnabled(true)
        option.captureActivity = CaptureQRCodeActivity::class.java
        barcodeLauncher.launch(option)
    }

    private fun deserializeMedicines(json: String): Pair<String, List<Medication>> {
        val gson = Gson()
        val jsonObject = JsonParser.parseString(json).asJsonObject
        val type = jsonObject.get("type").asString
        val medicationsJson = jsonObject.get("medications").asJsonArray
        val medications = medicationsJson.map { medicationJson ->
            gson.fromJson(medicationJson, Medication::class.java)
        }
        return Pair(type, medications)
    }

}