package com.syncdev.shifaa.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import com.syncdev.domain.model.Doctor
import com.syncdev.domain.model.Patient

class SharedPreferencesUtils {
    fun saveUserInformationToSharedPreferences(context: Context, user: FirebaseUser,role: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(Constants.NAVIGATION_STATE, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        // Save the relevant user information to SharedPreferences
        editor.putString(Constants.USER_UID, user.uid)
        editor.putString(Constants.USER, role)
        // Commit the changes
        editor.apply()
    }

    fun getFirebaseUserFromSharedPreferences(context: Context): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(Constants.NAVIGATION_STATE, Context.MODE_PRIVATE)

        val userUid: String? = sharedPreferences.getString(Constants.USER_UID, null)

        if (userUid != null) {
            return userUid
        }

        return null
    }

    fun savePatientToSharedPreferences(context: Context, patient: Patient) {
        val sharedPreferences = context.getSharedPreferences(Constants.USER_DATA, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val patientJson = gson.toJson(patient)
        editor.putString(Constants.PATIENT_DATA, patientJson)
        editor.apply()
    }

    fun getPatientFromSharedPreferences(context: Context): Patient? {
        val sharedPreferences = context.getSharedPreferences(Constants.USER_DATA, Context.MODE_PRIVATE)
        val patientJson = sharedPreferences.getString(Constants.DOCTOR_DATA, null)
        val gson = Gson()
        return gson.fromJson(patientJson, Patient::class.java)
    }

    fun saveDoctorToSharedPreferences(context: Context, doctor: Doctor) {
        val sharedPreferences = context.getSharedPreferences(Constants.USER_DATA, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val doctorJson = gson.toJson(doctor)
        editor.putString(Constants.DOCTOR_DATA, doctorJson)
        editor.apply()
    }

    fun getDoctorFromSharedPreferences(context: Context): Doctor? {
        val sharedPreferences = context.getSharedPreferences(Constants.USER_DATA, Context.MODE_PRIVATE)
        val doctorJson = sharedPreferences.getString(Constants.DOCTOR_DATA, null)
        val gson = Gson()
        return gson.fromJson(doctorJson, Doctor::class.java)
    }
}