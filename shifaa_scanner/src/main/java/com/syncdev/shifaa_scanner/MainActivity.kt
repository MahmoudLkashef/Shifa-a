package com.syncdev.shifaa_scanner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.syncdev.shifaa_scanner.databinding.ActivityMainBinding
import com.syncdev.shifaa_scanner.emergency.EmergencyActivity
import com.syncdev.shifaa_scanner.model.MedicalHistory
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
                val deserializedData = deserializeData(result.contents)
                if (deserializedData != null) {
                    val (type, data) = deserializedData
                    when (type) {
                        "Medications" -> {
                            val intent = Intent(this, MedicationActivity::class.java)
                            val medicationList = data as List<Medication>
                            intent.putParcelableArrayListExtra(
                                "Medications",
                                ArrayList(medicationList)
                            )
                            startActivity(intent)
                        }
                        "MedicalCard" -> {
                            val medicalHistory = data as MedicalHistory
                            val intent = Intent(this, EmergencyActivity::class.java)
                            intent.putExtra("MedicalHistory", medicalHistory)
                            startActivity(intent)
                        }
                    }
                } else {
                    Snackbar.make(binding.root, "Invalid QR Code", Snackbar.LENGTH_LONG).show()
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


    private fun deserializeMedicalHistory(json: String): Pair<String, MedicalHistory> {
        val gson = Gson()
        val jsonObject = JsonParser.parseString(json).asJsonObject
        val type = jsonObject.get("type").asString
        val medicalCardJson = jsonObject.get("medicalCard").asJsonObject
        val medicalHistory = gson.fromJson(medicalCardJson, MedicalHistory::class.java)
        return Pair(type, medicalHistory)
    }

    private fun deserializeData(json: String): Pair<String, Any>? {
        return try {
            val gson = Gson()
            val jsonObject = JsonParser.parseString(json).asJsonObject
            val type = jsonObject.get("type").asString

             when (type) {
                "Medications" -> {
                    deserializeMedicines(json)
                }
                "MedicalCard" -> {
                    deserializeMedicalHistory(json)
                }
                else -> null
            }
        }catch (e:Exception){
            Log.i(TAG, "deserializeData: ${e.message}")
            null
        }
    }

}