package com.syncdev.data.repo.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.syncdev.domain.model.Patient
import com.syncdev.domain.model.Doctor
import com.syncdev.domain.repo.remote.RemoteRepository
import com.syncdev.domain.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of RemoteRepository interface that handles remote operations with Firebase Database and Authentication.
 * @param firebaseDatabase an instance of FirebaseDatabase to handle database operations
 * @param auth an instance of FirebaseAuth to handle authentication operations
 */
class RemoteRepositoryImp @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val auth: FirebaseAuth
) : RemoteRepository {

    /**
     * A suspend function that logs in a patient with the provided email and password.
     * @param email email of the patient to be logged in
     * @param password password of the patient to be logged in
     * @return a FirebaseUser object if the login is successful, otherwise null
     */
    override suspend fun loginPatient(email: String, password: String): FirebaseUser? {
        return withContext(Dispatchers.IO) {
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                val user = result.user
                Log.d("LoginPatient", "success")
                user
            } catch (e: Exception) {
                Log.w("LoginPatient", "failure ${e.message}")
                null
            }
        }
    }

    /**
     * A suspend function that logs in a doctor with the provided email and password.
     * @param email email of the doctor to be logged in
     * @param password password of the doctor to be logged in
     * @return a FirebaseUser object if the login is successful, otherwise null
     */
    override suspend fun loginDoctor(email: String, password: String): FirebaseUser? {
        return withContext(Dispatchers.IO) {
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                val user = result.user
                Log.d("LoginDoctor", "success")
                user
            } catch (e: Exception) {
                Log.w("LoginDoctor", "failure ${e.message}")
                null
            }
        }
    }

    /**
     * A suspend function that registers a patient with the provided patient data and password.
     * @param patient a Patient object that contains the patient data to be registered
     * @param password password of the patient to be registered
     * @return a FirebaseUser object if the registration is successful, otherwise null
     */
    override suspend fun registerPatient(patient: Patient, password: String): FirebaseUser? {
        return withContext(Dispatchers.IO) {
            try {
                val result = auth.createUserWithEmailAndPassword(patient.email, password).await()
                val user = result.user
                Log.d("RegisterPatient", "success")
                saveUserData(patient, Constants.PATIENTS_TABLE)
                user
            } catch (e: Exception) {
                Log.w("RegisterPatient", "failure ${e.message}")
                null
            }
        }
    }

    /**
     * A suspend function that registers a doctor with the provided doctor data and password.
     * @param doctor a Doctor object that contains the doctor data to be registered
     * @param password password of the doctor to be registered
     * @return a FirebaseUser object if the registration is successful, otherwise null
     */
    override suspend fun registerDoctor(doctor: Doctor, password: String): FirebaseUser? {
        return withContext(Dispatchers.IO) {
            try {
                val result = auth.createUserWithEmailAndPassword(doctor.email, password).await()
                val user = result.user
                Log.d("RegisterDoctor", "success")
                saveUserData(doctor, Constants.DOCTORS_TABLE)
                user
            } catch (e: Exception) {
                Log.w("RegisterDoctor", "failure ${e.message}")
                null
            }
        }
    }

    /**
     * Saves a data object to the Firebase Realtime Database at the specified [reference].
     *
     * @param model The data object to save.
     * @param reference The path to the Firebase Realtime Database reference where the object should be saved.
     */
    private fun saveUserData(model: Any, reference: String) {
        // Get a reference to the Firebase Realtime Database at the specified path
        val database = firebaseDatabase.getReference(reference)

        // Generate a unique key for the new child node and store it in the `id` variable
        val id = database.push().key!!

        when(model){
            is Patient -> {model.id = id}
            is Doctor -> {model.id = id}
        }

        // Set the value of the newly created child node to the `model` object using the `setValue()` method
        // on the child node reference obtained by appending the `id` to the `database` reference.
        database.child(id).setValue(model)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("SaveData", "Data saved successfully")
                } else {
                    Log.w("SaveData", "Failed to save data: ${task.exception}")
                }
            }
    }


}