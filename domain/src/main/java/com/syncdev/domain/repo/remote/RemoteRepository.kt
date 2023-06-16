package com.syncdev.domain.repo.remote

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseError
import com.syncdev.domain.model.Appointment
import com.syncdev.domain.model.AppointmentRequest
import com.syncdev.domain.model.Patient
import com.syncdev.domain.model.Doctor

/**
 * An interface representing a repository for accessing remote data sources.
 */
interface RemoteRepository {

    /**
     * Attempts to log in a patient with the specified email and password.
     *
     * @param email The email of the patient.
     * @param password The password of the patient.
     * @return The FirebaseUser object representing the logged in user, or null if login fails.
     */
    suspend fun loginPatient(email: String, password: String): FirebaseUser?

    /**
     * Attempts to log in a doctor with the specified email and password.
     *
     * @param email The email of the doctor.
     * @param password The password of the doctor.
     * @return The FirebaseUser object representing the logged in user, or null if login fails.
     */
    suspend fun loginDoctor(email: String, password: String): FirebaseUser?

    /**
     * Attempts to register a patient with the specified patient data and password.
     *
     * @param patient The patient data to be registered.
     * @param password The password to be associated with the new patient account.
     * @return The FirebaseUser object representing the registered user, or null if registration fails.
     */
    suspend fun registerPatient(patient: Patient, password: String): FirebaseUser?

    /**
     * Attempts to register a doctor with the specified doctor data and password.
     *
     * @param doctor The doctor data to be registered.
     * @param password The password to be associated with the new doctor account.
     * @return The FirebaseUser object representing the registered user, or null if registration fails.
     */
    suspend fun registerDoctor(doctor: Doctor, password: String): FirebaseUser?

    suspend fun searchDoctorById(doctorId: String, onDoctorLoaded: (Doctor?) -> Unit)

    suspend fun searchPatientById(patientId: String, onPatientLoaded: (Patient?) -> Unit)

    suspend fun signOut()

    suspend fun fetchDoctorsFromFirebase(callback: (List<Doctor>?, DatabaseError?) -> Unit)

    suspend fun createAppointmentRequest(appointmentRequest: AppointmentRequest): Boolean

    suspend fun updateDoctorDataById(doctor:Doctor): Boolean

    suspend fun getAppointmentRequestsByDoctorId(doctorId: String): List<AppointmentRequest>

    suspend fun deleteAppointmentRequest(doctorId: String, date: String, time: String): Boolean

    suspend fun createNewAppointment(appointment: Appointment): Boolean

    suspend fun updatePatientDataById(patient: Patient):Boolean

    suspend fun getPreservedAppointmentsDate(doctorId: String, date: String): List<String>

    suspend fun getAppointmentsByPatientAndState(patientId: String, state: String): List<Appointment>

    suspend fun cancelAppointmentById(appointmentId: String): Boolean

    suspend fun updateDoctorRating(doctorId: String, newRating: Float)

    suspend fun rescheduleAppointment(appointmentId: String, date: String, time: String): Boolean

    suspend fun getAppointmentsByDoctorId(doctorId: String): List<Appointment>

    suspend fun getAppointmentsByDoctorAndDate(doctorId: String, date: String): List<Appointment>

}
