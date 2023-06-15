package com.syncdev.shifaa.utils.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.syncdev.domain.model.Medication
import com.syncdev.shifaa.R

/**
 * A [CoroutineWorker] subclass responsible for scheduling medication notifications.
 *
 * @param appContext The application [Context].
 * @param workerParams The [WorkerParameters] passed to the worker.
 */
class NotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    /**
     * Performs the work in the background.
     */
    override suspend fun doWork(): Result {
        // Retrieve the medication data from the input data
        val medicationsJson = inputData.getString(KEY_MEDICATION_LIST)
        val medication = Gson().fromJson<Medication>(
            medicationsJson,
            object : TypeToken<Medication>() {}.type
        )

        // Schedule notifications for the medication
        scheduleNotificationsForMedication(medication)

        return Result.success()
    }

    /**
     * Schedules notifications for the given [medication].
     */
    private fun scheduleNotificationsForMedication(medication: Medication) {
        val medicationName = medication.name
        val medicationType = medication.type
        val dosageTimes = medication.dosage.toIntOrNull() ?: 0
        val periodDays = medication.period.split(" ")[0].toIntOrNull() ?: 0
        val intervalMillis = (24 * 60 * 60 * 1000) / dosageTimes
        val currentTime = System.currentTimeMillis()

        for (i in 1..periodDays) {
            for (j in 1..dosageTimes) {
                val notificationId = generateNotificationId(
                    medicationName,
                    medicationType,
                    i,
                    j
                )
                val notificationTime = currentTime + (intervalMillis * ((i - 1) * dosageTimes + j))
                val notificationBuilder = buildNotification(
                    applicationContext,
                    medicationName,
                    medicationType
                )
                val alarmManager =
                    applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val notificationIntent = Intent(applicationContext, NotificationReceiver::class.java)
                notificationIntent.putExtra("notificationId", notificationId)
                notificationIntent.putExtra("notification", notificationBuilder.build())
                val pendingNotificationIntent = PendingIntent.getBroadcast(
                    applicationContext,
                    notificationId,
                    notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

                // Set the alarm to trigger the notification
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        notificationTime,
                        pendingNotificationIntent
                    )
                } else {
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        notificationTime,
                        pendingNotificationIntent
                    )
                }
            }
        }
    }

    /**
     * Builds the notification using the provided parameters.
     */
    private fun buildNotification(
        context: Context,
        medicationName: String?,
        medicationType: String?
    ): NotificationCompat.Builder {
        createNotificationChannel(context)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Medication reminder")
            .setContentText("It's time to take your medication: $medicationName ($medicationType)")
            .setSmallIcon(R.drawable.notification_ic)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
    }

    /**
     * Creates the notification channel for Android Oreo (API 26) and above.
     */
    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Shifaa"
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Generates a unique notification ID based on the medication details and dosage index.
     */
    private fun generateNotificationId(
        medicationName: String,
        medicationType: String,
        day: Int,
        dosageIndex: Int
    ): Int {
        return medicationName.hashCode() + medicationType.hashCode() + day + dosageIndex
    }

    companion object {
        private const val CHANNEL_ID = "MedicationChannel"
        private const val CHANNEL_NAME = "Medication Notifications"
        const val KEY_MEDICATION_LIST = "medicationList"
    }
}
