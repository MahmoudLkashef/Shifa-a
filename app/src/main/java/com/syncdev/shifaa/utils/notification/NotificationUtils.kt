package com.syncdev.shifaa.utils.notification

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.gson.Gson
import com.syncdev.domain.model.Medication

class NotificationUtils(private val context: Context) {
    private fun createMedicationReminders(medications: List<Medication>) {
        medications.forEach { medication ->

            // Convert the list of medications to a JSON string
            val medicationsJson = Gson().toJson(medication)

            // Create the input data
            val inputData = Data.Builder()
                .putString(NotificationWorker.KEY_MEDICATION_LIST, medicationsJson)
                .build()

            // Create the work request
            val notificationWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInputData(inputData)
                .build()

            // Schedule the work request
            WorkManager.getInstance(context).enqueue(notificationWorkRequest)
        }
    }
}