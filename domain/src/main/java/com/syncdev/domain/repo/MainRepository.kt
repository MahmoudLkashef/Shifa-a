package com.syncdev.domain.repo

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseError
import com.syncdev.domain.model.Appointment
import com.syncdev.domain.model.AppointmentRequest
import com.syncdev.domain.model.Doctor
import com.syncdev.domain.model.MedicalHistory
import com.syncdev.domain.model.Patient
import com.syncdev.domain.model.Prescription
import com.syncdev.domain.model.ScheduledMedication

interface MainRepository {

    //region Local
    suspend fun insertScheduledMedications(scheduledMedications: List<ScheduledMedication>)

    suspend fun getAllScheduledMedications(): List<ScheduledMedication>

    suspend fun getScheduledMedicationById(scheduledMedicationId: Int): ScheduledMedication
    //endregion

    //region Remote
    suspend fun loginPatient(email: String, password: String): FirebaseUser?

    suspend fun loginDoctor(email: String, password: String): FirebaseUser?

    suspend fun registerPatient(patient: Patient, password: String): FirebaseUser?

    suspend fun registerDoctor(doctor: Doctor, password: String): FirebaseUser?

    suspend fun searchDoctorById(doctorId: String, onDoctorLoaded: (Doctor?) -> Unit)

    suspend fun searchPatientById(patientId: String, onPatientLoaded: (Patient?) -> Unit)

    suspend fun signOut()

    suspend fun fetchDoctorsFromFirebase(callback: (List<Doctor>?, DatabaseError?) -> Unit)

    suspend fun createAppointmentRequest(appointmentRequest: AppointmentRequest): Boolean

    suspend fun updateDoctorDataById(doctor: Doctor): Boolean

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

    suspend fun savePrescription(prescription: Prescription, appointmentId:String, patientId: String):Boolean

    suspend fun updateAppointmentState(appointmentId: String, newState: String)

    suspend fun getCompletedAppointmentsByDoctorId(doctorId: String): List<Appointment>

    suspend fun getCompletedAppointmentsByPatientId(patientId: String): List<Appointment>

    suspend fun getPatientMedicalHistory(patientId:String,callback: (MedicalHistory?) -> Unit)

    suspend fun updatePatientMedicalHistory(patientId: String,medicalHistory: MedicalHistory):Boolean

    suspend fun updatePatientChronicDiseases(patientId: String,chronicDiseases:List<String>):Boolean

    suspend fun updateEmergencyContacts(patientId:String,emergencyContacts:List<String>):Boolean
    //endregion
}