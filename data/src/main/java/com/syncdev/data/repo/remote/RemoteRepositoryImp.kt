package com.syncdev.data.repo.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.syncdev.domain.model.AppointmentRequest
import com.syncdev.domain.model.Patient
import com.syncdev.domain.model.Doctor
import com.syncdev.domain.repo.remote.RemoteRepository
import com.syncdev.domain.utils.Constants
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    private val TAG="RemoteRepositoryImp"
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
                val database = firebaseDatabase.reference.child("Patients")
                val deferred = CompletableDeferred<Boolean>()

                database.orderByChild("email").equalTo(email)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val exists = snapshot.exists()
                            Log.i("loginPatient", "onDataChange: $exists")
                            deferred.complete(exists)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.i("loginPatient", "onCancelled: ${error.message} ")
                            deferred.complete(false)
                        }
                    })

                val exists = deferred.await()
                Log.i("loginPatient", "exists: $exists")

                if(exists) user
                else null
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
                val database = firebaseDatabase.reference.child("Doctors")
                val deferred = CompletableDeferred<Boolean>()

                database.orderByChild("email").equalTo(email)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val exists = snapshot.exists()
                            Log.i("loginDoctor", "onDataChange: $exists")
                            deferred.complete(exists)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.i("loginDoctor", "onCancelled: ${error.message} ")
                            deferred.complete(false)
                        }
                    })

                val exists = deferred.await()
                Log.i("loginDoctor", "exists: $exists")

                if (exists) {
                    user
                } else {
                    null
                }
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
                if (user != null) {
                    saveUserData(patient, Constants.PATIENTS_TABLE, user.uid)
                }
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
                if(user!=null){
                    saveUserData(doctor, Constants.DOCTORS_TABLE, user.uid)
                }
                user
            } catch (e: Exception) {
                Log.w("RegisterDoctor", "failure ${e.message}")
                null
            }
        }
    }

    override suspend fun searchDoctorById(
        doctorId: String,
        onDoctorLoaded: (Doctor?) -> Unit
    ){
        val database = firebaseDatabase.reference.child("Doctors")
        database.orderByChild("id").equalTo(doctorId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val doctor = snapshot.children.firstOrNull()?.getValue(Doctor::class.java)
                    onDoctorLoaded(doctor)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle any errors that occurred during retrieval
                    onDoctorLoaded(null)
                }
            })
    }

    override suspend fun searchPatientById(
        patientId: String,
        onPatientLoaded: (Patient?) -> Unit
    ){
        val database = firebaseDatabase.reference.child("Patients")
        database.orderByChild("id").equalTo(patientId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val patient = snapshot.children.firstOrNull()?.getValue(Patient::class.java)
                    onPatientLoaded(patient)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle any errors that occurred during retrieval
                    onPatientLoaded(null)
                }
            })
    }

    override suspend fun signOut() {
        auth.signOut()
    }


    override suspend fun fetchDoctorsFromFirebase(callback: (List<Doctor>?, DatabaseError?) -> Unit) {
        val database = firebaseDatabase.reference.child("Doctors")

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val doctorsList: MutableList<Doctor> = mutableListOf()

                for (doctorSnapshot in snapshot.children) {
                    val doctor = doctorSnapshot.getValue(Doctor::class.java)
                    doctor?.let {
                        doctorsList.add(it)
                    }
                }

                callback(doctorsList, null)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, error)
            }
        })
    }

    /**
     * Creates an appointment request by saving the provided [appointmentRequest] object to the Firebase Realtime Database.
     * Returns a [Boolean] value indicating whether the data is saved successfully or not.
     *
     * @param appointmentRequest The appointment request object to be saved.
     * @return True if the data is saved successfully, false otherwise.
     */
    override suspend fun createAppointmentRequest(appointmentRequest: AppointmentRequest): Boolean {
        // Get a reference to the Firebase Realtime Database at the specified path
        val database = firebaseDatabase.getReference("Appointment Requests")

        // Create a CompletableDeferred<Boolean> to hold the result of the asynchronous operation
        val deferred = CompletableDeferred<Boolean>()

        // Generate a unique ID for the appointment request
        val id = database.push().key!!

        // Set the value of the newly created child node to the `appointmentRequest` object using the `setValue()` method
        // on the child node reference obtained by appending the `id` to the `database` reference.
        database.child(id).setValue(appointmentRequest)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Data saved successfully
                    Log.d("createAppointmentRequest", "Data saved successfully")
                    deferred.complete(true) // Complete the deferred with a value of true
                } else {
                    // Failed to save data
                    Log.w("createAppointmentRequest", "Failed to save data: ${task.exception}")
                    deferred.complete(false) // Complete the deferred with a value of false
                }
            }

        // Suspend the coroutine and wait for the result of the deferred operation
        return deferred.await()
    }


    /**
    Updates the data of a doctor in the database based on their ID.
    @param doctor The updated Doctor object containing the new data.
    @return Boolean value indicating whether the update was successful or not.
     */
    override suspend fun updateDoctorDataById(doctor: Doctor): Boolean {
        return try {
            val database = firebaseDatabase.reference.child("Doctors")
            val doctorData = mapOf<String, Any>(
                "firstName" to doctor.firstName,
                "lastName" to doctor.lastName,
                "phoneNumber" to doctor.phoneNumber,
                "speciality" to doctor.speciality,
                "yearsOfExperience" to doctor.yearsOfExperience,
                "aboutDoctor" to doctor.aboutDoctor
            )

            database.child(doctor.id.toString()).updateChildren(doctorData).await()
            true
        } catch (e: Exception) {
            Log.i(TAG, "updateDoctorDataById: ${e.message}")
            false
        }
    }


    /**
    Updates the data of a patient in the database based on their ID.
    @param patient The updated Patient object containing the new data.
    @return Boolean value indicating whether the update was successful or not.
     */
    override suspend fun updatePatientDataById(patient: Patient): Boolean {
        return try {
            val database = firebaseDatabase.reference.child("Patients")
            val patientData = mapOf<String, Any>(
                "firstName" to patient.firstName,
                "lastName" to patient.lastName,
                "phoneNumber" to patient.phoneNumber,
                "age" to patient.age
            )

            database.child(patient.id.toString()).updateChildren(patientData).await()
            true
        } catch (e: Exception) {
            Log.i(TAG, "updateDoctorDataById: ${e.message}")
            false
        }
    }

    /**
     * Saves a data object to the Firebase Realtime Database at the specified [reference].
     *
     * @param model The data object to save.
     * @param reference The path to the Firebase Realtime Database reference where the object should be saved.
     */
    private fun saveUserData(model: Any, reference: String, id: String) {
        // Get a reference to the Firebase Realtime Database at the specified path
        val database = firebaseDatabase.getReference(reference)

        // Generate a unique key for the new child node and store it in the `id` variable
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