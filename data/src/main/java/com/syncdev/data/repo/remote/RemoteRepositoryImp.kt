package com.syncdev.data.repo.remote

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.syncdev.domain.model.*
import com.syncdev.domain.repo.remote.RemoteRepository
import com.syncdev.domain.utils.Constants
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Implementation of RemoteRepository interface that handles remote operations with Firebase Database and Authentication.
 * @param firebaseDatabase an instance of FirebaseDatabase to handle database operations
 * @param auth an instance of FirebaseAuth to handle authentication operations
 */
class RemoteRepositoryImp @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val auth: FirebaseAuth
) : RemoteRepository {

    private val TAG = "RemoteRepositoryImp"

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

                if (exists) user
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
                if (user != null) {
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
    ) {
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
    ) {
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
     * Retrieves a list of AppointmentRequest objects with a specific doctorId.
     *
     * @param doctorId The ID of the doctor to filter the appointment requests.
     * @return A list of AppointmentRequest objects matching the doctorId.
     */
    override suspend fun getAppointmentRequestsByDoctorId(doctorId: String): List<AppointmentRequest> {
        val database = FirebaseDatabase.getInstance()
        val appointmentRequestsRef = database.getReference("Appointment Requests")
        val appointmentRequests: MutableList<AppointmentRequest> = mutableListOf()

        // Query the database to get the appointments with matching doctorId
        val query: Query = appointmentRequestsRef.orderByChild("doctorId").equalTo(doctorId)

        // Use a suspendCoroutine to wrap the asynchronous operation
        return suspendCoroutine { continuation ->
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (appointmentSnapshot in snapshot.children) {
                        val appointment =
                            appointmentSnapshot.getValue(AppointmentRequest::class.java)

                        // Retrieve nested patient information
                        val patientSnapshot = appointmentSnapshot.child("patient")
                        val patient = patientSnapshot.getValue(Patient::class.java)

                        appointment?.let {
                            if (patient != null) {
                                it.patient =
                                    patient // Assign the patient object to the appointment request
                            }
                            appointmentRequests.add(it)
                        }
                    }

                    continuation.resume(appointmentRequests)
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWithException(error.toException())
                }
            })
        }
    }


    override suspend fun deleteAppointmentRequest(
        doctorId: String,
        date: String,
        time: String
    ): Boolean {
        val database = FirebaseDatabase.getInstance()
        val appointmentRequestsRef = database.getReference("Appointment Requests")

        // Query the database to find the appointment requests with matching doctorId
        val query: Query = appointmentRequestsRef
            .orderByChild("doctorId")
            .equalTo(doctorId)

        // Use a suspendCoroutine to wrap the asynchronous operation
        return suspendCoroutine { continuation ->
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Iterate over the appointment requests with matching doctorId
                    for (appointmentSnapshot in snapshot.children) {
                        val appointment =
                            appointmentSnapshot.getValue(AppointmentRequest::class.java)

                        // Check if the appointment matches the provided date and time
                        if (appointment?.date == date && appointment.time == time) {
                            appointmentSnapshot.ref.removeValue() // Delete the appointment request
                        }
                    }
                    continuation.resume(true) // Resume the coroutine with true indicating success
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resume(false) // Resume the coroutine with false indicating failure
                }
            })
        }
    }

    override suspend fun createNewAppointment(appointment: Appointment): Boolean {
        val database = FirebaseDatabase.getInstance()
        val appointmentsRef = database.getReference("Appointments")

        // Generate a new unique ID for the appointment
        val appointmentId = appointmentsRef.push().key ?: return false

        // Set the appointment ID
        appointment.id = appointmentId

        // Set the appointment state to be Upcoming
        appointment.state = "Upcoming"

        // Use a suspendCoroutine to wrap the asynchronous operation
        return suspendCoroutine { continuation ->
            appointmentsRef.child(appointmentId).setValue(appointment)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(true) // Resume the coroutine with true indicating success
                    } else {
                        continuation.resume(false) // Resume the coroutine with false indicating failure
                    }
                }
        }
    }

    /**
     * Updates the data of a doctor in the database based on their ID.
     * @param doctor The updated Doctor object containing the new data.
     * @return Boolean value indicating whether the update was successful or not.
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
     * Updates the data of a patient in the database based on their ID.
     * @param patient The updated Patient object containing the new data.
     * @return Boolean value indicating whether the update was successful or not.
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

    override suspend fun getPreservedAppointmentsDate(
        doctorId: String,
        date: String
    ): List<String> =
        suspendCancellableCoroutine { continuation ->
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val appointmentsRef: DatabaseReference = database.getReference("Appointments")

            val query = appointmentsRef.orderByChild("doctor/id").equalTo(doctorId)

            val eventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val preservedAppointments: MutableList<String> = mutableListOf()

                    for (appointmentSnapshot in dataSnapshot.children) {
                        val appointmentDate = appointmentSnapshot.child("date").value as? String
                        val appointmentTime = appointmentSnapshot.child("time").value as? String
                        val appointmentState = appointmentSnapshot.child("state").value as? String

                        if (appointmentDate == date && appointmentTime != null && appointmentState == "Upcoming") {
                            preservedAppointments.add(appointmentTime)
                        }
                    }

                    continuation.resume(preservedAppointments)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    continuation.resumeWithException(databaseError.toException())
                }
            }

            query.addListenerForSingleValueEvent(eventListener)

            continuation.invokeOnCancellation {
                query.removeEventListener(eventListener)
            }
        }

    override suspend fun getAppointmentsByPatientAndState(
        patientId: String,
        state: String
    ): List<Appointment> =
        suspendCancellableCoroutine { continuation ->
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val appointmentsRef: DatabaseReference = database.getReference("Appointments")

            val query = appointmentsRef.orderByChild("patient/id").equalTo(patientId)

            val currentTime = getCurrentTime()
            val currentDate = getCurrentDate()

            val eventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val appointments: MutableList<Appointment> = mutableListOf()

                    for (appointmentSnapshot in dataSnapshot.children) {
                        val appointment = appointmentSnapshot.getValue(Appointment::class.java)
                        if (appointment != null && appointment.state == state) {
                            if (state == "Upcoming" && !isAppointmentUpcoming(
                                    appointment.date,
                                    appointment.time,
                                    currentDate,
                                    currentTime
                                )
                            ) {
                                appointmentSnapshot.ref.removeValue()
                            } else {
                                appointments.add(appointment)
                            }
                        }
                    }

                    continuation.resume(appointments)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    continuation.resumeWithException(databaseError.toException())
                }
            }

            query.addListenerForSingleValueEvent(eventListener)

            continuation.invokeOnCancellation {
                query.removeEventListener(eventListener)
            }
        }

    override suspend fun getAppointmentsByDoctorAndDate(
        doctorId: String,
        date: String
    ): List<Appointment> =
        suspendCancellableCoroutine { continuation ->
            val appointmentsRef = FirebaseDatabase.getInstance().reference.child("Appointments")

            val query: Query = appointmentsRef.orderByChild("doctor/id").equalTo(doctorId)

            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val appointments = mutableListOf<Appointment>()

                    for (appointmentSnapshot in dataSnapshot.children) {
                        val appointment = appointmentSnapshot.getValue(Appointment::class.java)
                        if (appointment != null && appointment.date == date && appointment.state == "Upcoming") {
                            appointments.add(appointment)
                        }
                    }

                    continuation.resume(appointments)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    continuation.resumeWithException(databaseError.toException())
                }
            }

            query.addListenerForSingleValueEvent(valueEventListener)

            continuation.invokeOnCancellation {
                query.removeEventListener(valueEventListener)
            }
        }

    override suspend fun savePrescription(
        prescription: Prescription,
        appointmentId: String,
        patientId: String
    ): Boolean {
        return try {
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val appointmentsRef: DatabaseReference = database.getReference("Appointments")
            val prescriptionsRef = database.getReference("Prescriptions")
            val prescriptionRef =
                appointmentsRef.child(appointmentId).child("prescription")
            val patientRef=database.getReference("Patients").child(patientId).child("prescription")

            // Add unique key to prescription
            prescription.id = prescriptionRef.push().key

            // Convert prescription object to a map
            val prescriptionMap = prescription.toMap(prescription)

            // Create prescriptions root and append prescription to it
            prescriptionsRef.child(prescription.id.toString()).setValue(prescriptionMap)

            // Append prescription to patient
            patientRef.setValue(prescriptionMap)

            // Set the value of the prescription reference with the prescription map
            prescriptionRef.setValue(prescriptionMap)

            // Update appointment state
            updateAppointmentState(appointmentId, "Completed")

            true

        } catch (e: Exception) {
            Log.i(TAG, "savePrescription: ${e.message}")
            false
        }
    }

    override suspend fun updateAppointmentState(appointmentId: String, newState: String) {
        try {
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val appointmentsRef: DatabaseReference = database.getReference("Appointments")
            val appointmentRef = appointmentsRef.child(appointmentId).child("state")

            // Update the value of the state reference with the new state
            appointmentRef.setValue(newState)
        } catch (e: Exception) {
            Log.i(TAG, "updateAppointmentState: ${e.message}")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getPatientMedicalHistory(
        patientId: String,
        callback: (MedicalHistory?) -> Unit
    ) {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()

        val medicalHistoryRef = database.getReference("PatientMedicalHistory/$patientId")

        var patientAge = ""
        searchPatientById(patientId) {
            it?.let {
                patientAge = calculateAge(it.age).toString()
            }
        }

        medicalHistoryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val medicalHistory = dataSnapshot.getValue(MedicalHistory::class.java)
                medicalHistory?.age = patientAge

                if (medicalHistory == null) {
                    val newMedicalHistory = MedicalHistory(
                        bloodType = "",
                        age = patientAge,
                        height = "",
                        weight = "",
                        chronicDiseases = emptyList(),
                        medication = emptyList<Medication>()
                    )

                    medicalHistoryRef.setValue(newMedicalHistory)
                        .addOnSuccessListener {
                            callback(newMedicalHistory)
                        }
                        .addOnFailureListener { exception ->
                            Log.e(TAG, "Failed to create medical history: ${exception.message}")
                            callback(null)
                        }
                } else {
                    callback(medicalHistory)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i(TAG, "onCancelled: ${databaseError.message}")
                callback(null)
            }
        })

    }

    override suspend fun updatePatientMedicalHistory(
        patientId: String,
        medicalHistory: MedicalHistory
    ): Boolean {
        return try {
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()

            val medicalHistoryRef = database.getReference("PatientMedicalHistory/$patientId")

            val updates: MutableMap<String, Any?> = HashMap()

            if (!medicalHistory.bloodType.isNullOrEmpty()) {
                updates["bloodType"] = medicalHistory.bloodType
            }

            if (!medicalHistory.height.isNullOrEmpty()) {
                updates["height"] = medicalHistory.height
            }

            if (!medicalHistory.weight.isNullOrEmpty()) {
                updates["weight"] = medicalHistory.weight
            }

            val completable = CompletableDeferred<Boolean>()

            medicalHistoryRef.updateChildren(updates)
                .addOnSuccessListener {
                    // Success
                    Log.i(TAG, "Patient medical history updated successfully")
                    completable.complete(true)
                }
                .addOnFailureListener { e ->
                    // Error
                    Log.e(TAG, "Failed to update patient medical history: ${e.message}")
                    completable.complete(false)
                }

            completable.await()
        } catch (e: Exception) {
            Log.e(TAG, "updatePatientMedicalHistory: ${e.message}")
            false
        }
    }


    override suspend fun updatePatientChronicDiseases(
        patientId: String,
        chronicDiseases: List<String>
    ): Boolean {
        return try {
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()

            val medicalHistoryRef = database.getReference("PatientMedicalHistory/$patientId")

            return suspendCancellableCoroutine { continuation ->
                medicalHistoryRef.child("chronicDiseases").setValue(chronicDiseases)
                    .addOnSuccessListener {
                        continuation.resume(true)
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Failed to update chronic diseases: ${e.message}")
                        continuation.resume(false)
                    }
            }
        } catch (e: Exception) {
            Log.i(TAG, "updatePatientChronicDiseases: ${e.message}")
            false
        }
    }

    private fun Prescription.toMap(prescription: Prescription): Map<String, Any?> {
        return mapOf(
            "id" to prescription.id,
            "patientId" to prescription.patientId,
            "doctorId" to prescription.doctorId,
            "medicines" to prescription.medicines.map { it.toMap(it) },
            "advice" to prescription.advice
        )
    }

    private fun Medication.toMap(medication: Medication): Map<String, Any?> {
        return mapOf(
            "name" to medication.name,
            "type" to medication.type,
            "period" to medication.period,
            "dosage" to medication.dosage,
            "frequency" to medication.frequency,
            "scheduleLabels" to medication.scheduleLabels
        )
    }

    override suspend fun cancelAppointmentById(appointmentId: String): Boolean {
        return suspendCancellableCoroutine { continuation ->
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val appointmentsRef: DatabaseReference = database.getReference("Appointments")

            val appointmentToUpdateRef = appointmentsRef.child(appointmentId)
            val updatedData = mapOf("state" to "Canceled")

            appointmentToUpdateRef.updateChildren(updatedData)
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnFailureListener { exception ->
                    continuation.resume(false)
                    Log.i("cancelAppointmentById", "cancelAppointmentById: ${exception.message}")
                }
        }
    }

    override suspend fun updateDoctorRating(doctorId: String, newRating: Float) {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val doctorsRef: DatabaseReference = database.getReference("Doctors")

        val doctorQuery = doctorsRef.child(doctorId)
        doctorQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val doctor = dataSnapshot.getValue(Doctor::class.java)
                if (doctor != null) {
                    val totalRating = doctor.totalRating + newRating
                    val numOfReviews = doctor.numOfReviews + 1

                    // Update the doctor's total rating and number of reviews
                    doctorQuery.child("totalRating").setValue(totalRating)
                    doctorQuery.child("numOfReviews").setValue(numOfReviews)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle cancellation or error
                Log.i("updateDoctorRating", "onCancelled: ${databaseError.message}")
            }
        })
    }

    override suspend fun rescheduleAppointment(
        appointmentId: String,
        date: String,
        time: String
    ): Boolean =
        suspendCancellableCoroutine { continuation ->
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val appointmentsRef: DatabaseReference = database.getReference("Appointments")

            val appointmentQuery = appointmentsRef.child(appointmentId)
            appointmentQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val appointment = dataSnapshot.getValue(Appointment::class.java)
                    if (appointment != null) {
                        // Update the date and time values of the appointment
                        appointmentQuery.child("date").setValue(date)
                        appointmentQuery.child("time").setValue(time)
                        continuation.resume(true) // Update successful
                    } else {
                        continuation.resume(false) // Appointment not found
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    continuation.resume(false) // Update cancelled or error occurred
                    Log.i("rescheduleAppointment", "onCancelled: ${databaseError.message}")
                }
            })

            continuation.invokeOnCancellation {
                // Handle cancellation if needed
            }
        }

    override suspend fun getAppointmentsByDoctorId(doctorId: String): List<Appointment> =
        suspendCancellableCoroutine { continuation ->
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val appointmentsRef: DatabaseReference = database.getReference("Appointments")

            val query = appointmentsRef.orderByChild("doctor/id").equalTo(doctorId)

            val eventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val appointments: MutableList<Appointment> = mutableListOf()

                    for (appointmentSnapshot in dataSnapshot.children) {
                        val appointment = appointmentSnapshot.getValue(Appointment::class.java)
                        if (appointment != null && appointment.state == "Upcoming") {
                            appointments.add(appointment)
                        }
                    }

                    continuation.resume(appointments)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    continuation.resumeWithException(databaseError.toException())
                }
            }

            query.addListenerForSingleValueEvent(eventListener)

            continuation.invokeOnCancellation {
                query.removeEventListener(eventListener)
            }
        }

    override suspend fun getCompletedAppointmentsByDoctorId(doctorId: String): List<Appointment> {
        return suspendCoroutine { continuation ->
            val database: DatabaseReference = FirebaseDatabase.getInstance().reference
            val appointmentsRef: DatabaseReference = database.child("Appointments")

            val query: Query = appointmentsRef.orderByChild("doctor/id").equalTo(doctorId)

            val eventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val appointmentList = mutableListOf<Appointment>()
                    for (appointmentSnapshot in dataSnapshot.children) {
                        val appointment = appointmentSnapshot.getValue(Appointment::class.java)
                        if (appointment?.state == "Completed") {
                            appointmentList.add(appointment)
                        }
                    }
                    continuation.resume(appointmentList)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    continuation.resumeWithException(databaseError.toException())
                }
            }

            query.addListenerForSingleValueEvent(eventListener)
        }
    }

    override suspend fun getCompletedAppointmentsByPatientId(patientId: String): List<Appointment> {
        return suspendCoroutine { continuation ->
            val database: DatabaseReference = FirebaseDatabase.getInstance().reference
            val appointmentsRef: DatabaseReference = database.child("Appointments")

            val query: Query = appointmentsRef.orderByChild("patient/id").equalTo(patientId)

            val eventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val appointmentList = mutableListOf<Appointment>()
                    for (appointmentSnapshot in dataSnapshot.children) {
                        val appointment = appointmentSnapshot.getValue(Appointment::class.java)
                        if (appointment?.state == "Completed") {
                            appointmentList.add(appointment)
                        }
                    }
                    continuation.resume(appointmentList)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    continuation.resumeWithException(databaseError.toException())
                }
            }

            query.addListenerForSingleValueEvent(eventListener)
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
        when (model) {
            is Patient -> {
                model.id = id
            }
            is Doctor -> {
                model.id = id
            }
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


    private fun isAppointmentUpcoming(
        appointmentDate: String,
        appointmentTime: String,
        currentDate: String,
        currentTime: String
    ): Boolean {
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val parsedAppointmentDate = dateFormat.parse(appointmentDate)
        val parsedCurrentDate = dateFormat.parse(currentDate)

        return if (parsedAppointmentDate == parsedCurrentDate) {
            val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            val parsedAppointmentTime = timeFormat.parse(appointmentTime)
            val parsedCurrentTime = timeFormat.parse(currentTime)
            parsedAppointmentTime.after(parsedCurrentTime)
        } else {
            parsedAppointmentDate.after(parsedCurrentDate)
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }

    private fun getCurrentTime(): String {
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val currentTime = Date()
        return timeFormat.format(currentTime)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateAge(dateOfBirth: String): Int? {
        val formatter = DateTimeFormatter.ofPattern("d-M-yyyy")
        val currentDate = LocalDate.now()
        val birthDate = LocalDate.parse(dateOfBirth, formatter)

        val period = Period.between(birthDate, currentDate)
        return period.years
    }

}