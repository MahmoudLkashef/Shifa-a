package com.syncdev.shifaa.utils

import android.os.Build
import com.google.firebase.auth.FirebaseUser

object Constants {
    const val NAVIGATION_STATE = "navigationState"
    const val PATIENT = "patient"
    const val DOCTOR = "doctor"
    const val USER = "user"
    const val USER_UID = "userUID"
    const val USER_DATA= "userData"
    const val PATIENT_DATA = "patientData"
    const val DOCTOR_DATA = "patientData"

    //checking if the api is 31 or higher
    //to handle the pending intent flag
    val RUNNING_S_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    //checking if the api is 33 or higher
    //used to know when to request notification permission
    //since from api 33 notification permission is runtime permission
    val RUNNING_TIRAMISU_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    //notification permission
    const val NOTIFICATION_REQUEST = 2
    const val NOTIFICATION_PERMISSION = android.Manifest.permission.POST_NOTIFICATIONS
}