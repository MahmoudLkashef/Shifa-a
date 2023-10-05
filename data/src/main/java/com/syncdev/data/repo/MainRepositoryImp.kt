package com.syncdev.data.repo

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseError
import com.syncdev.domain.local.LocalDataSource
import com.syncdev.domain.model.Appointment
import com.syncdev.domain.model.AppointmentRequest
import com.syncdev.domain.model.Doctor
import com.syncdev.domain.model.MedicalHistory
import com.syncdev.domain.model.Patient
import com.syncdev.domain.model.Prescription
import com.syncdev.domain.model.ScheduledMedication
import com.syncdev.domain.remote.RemoteDataSource
import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class MainRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MainRepository {

    //region Local
    override suspend fun insertScheduledMedications(scheduledMedications: List<ScheduledMedication>) =
        localDataSource.insertScheduledMedications(scheduledMedications)

    override suspend fun getAllScheduledMedications(): List<ScheduledMedication> =
        localDataSource.getAllScheduledMedications()

    override suspend fun getScheduledMedicationById(scheduledMedicationId: Int): ScheduledMedication =
        localDataSource.getScheduledMedicationById(scheduledMedicationId)
    //endregion

    //region Remote
    override suspend fun loginPatient(email: String, password: String): FirebaseUser? =
        remoteDataSource.loginPatient(email, password)

    override suspend fun loginDoctor(email: String, password: String): FirebaseUser? =
        remoteDataSource.loginDoctor(email, password)

    override suspend fun registerPatient(patient: Patient, password: String): FirebaseUser? =
        remoteDataSource.registerPatient(patient, password)

    override suspend fun registerDoctor(doctor: Doctor, password: String): FirebaseUser? =
        remoteDataSource.registerDoctor(doctor, password)

    override suspend fun searchDoctorById(doctorId: String, onDoctorLoaded: (Doctor?) -> Unit) =
        remoteDataSource.searchDoctorById(doctorId, onDoctorLoaded)

    override suspend fun searchPatientById(patientId: String, onPatientLoaded: (Patient?) -> Unit) =
        remoteDataSource.searchPatientById(patientId, onPatientLoaded)

    override suspend fun signOut() = remoteDataSource.signOut()

    override suspend fun fetchDoctorsFromFirebase(callback: (List<Doctor>?, DatabaseError?) -> Unit) =
        remoteDataSource.fetchDoctorsFromFirebase(callback)

    override suspend fun createAppointmentRequest(appointmentRequest: AppointmentRequest): Boolean =
        remoteDataSource.createAppointmentRequest(appointmentRequest)

    override suspend fun updateDoctorDataById(doctor: Doctor): Boolean =
        remoteDataSource.updateDoctorDataById(doctor)

    override suspend fun getAppointmentRequestsByDoctorId(doctorId: String): List<AppointmentRequest> =
        remoteDataSource.getAppointmentRequestsByDoctorId(doctorId)

    override suspend fun deleteAppointmentRequest(
        doctorId: String,
        date: String,
        time: String
    ): Boolean = remoteDataSource.deleteAppointmentRequest(doctorId, date, time)

    override suspend fun createNewAppointment(appointment: Appointment): Boolean =
        remoteDataSource.createNewAppointment(appointment)

    override suspend fun updatePatientDataById(patient: Patient): Boolean =
        remoteDataSource.updatePatientDataById(patient)

    override suspend fun getPreservedAppointmentsDate(
        doctorId: String,
        date: String
    ): List<String> = remoteDataSource.getPreservedAppointmentsDate(doctorId, date)

    override suspend fun getAppointmentsByPatientAndState(
        patientId: String,
        state: String
    ): List<Appointment> = remoteDataSource.getAppointmentsByPatientAndState(patientId, state)

    override suspend fun cancelAppointmentById(appointmentId: String): Boolean =
        remoteDataSource.cancelAppointmentById(appointmentId)

    override suspend fun updateDoctorRating(doctorId: String, newRating: Float) =
        remoteDataSource.updateDoctorRating(doctorId, newRating)

    override suspend fun rescheduleAppointment(
        appointmentId: String,
        date: String,
        time: String
    ): Boolean = remoteDataSource.rescheduleAppointment(appointmentId, date, time)

    override suspend fun getAppointmentsByDoctorId(doctorId: String): List<Appointment> =
        remoteDataSource.getAppointmentsByDoctorId(doctorId)

    override suspend fun getAppointmentsByDoctorAndDate(
        doctorId: String,
        date: String
    ): List<Appointment> = remoteDataSource.getAppointmentsByDoctorAndDate(doctorId, date)

    override suspend fun savePrescription(
        prescription: Prescription,
        appointmentId: String,
        patientId: String
    ): Boolean = remoteDataSource.savePrescription(prescription, appointmentId, patientId)

    override suspend fun updateAppointmentState(appointmentId: String, newState: String) =
        remoteDataSource.updateAppointmentState(appointmentId, newState)

    override suspend fun getCompletedAppointmentsByDoctorId(doctorId: String): List<Appointment> =
        remoteDataSource.getCompletedAppointmentsByDoctorId(doctorId)

    override suspend fun getCompletedAppointmentsByPatientId(patientId: String): List<Appointment> =
        remoteDataSource.getCompletedAppointmentsByPatientId(patientId)

    override suspend fun getPatientMedicalHistory(
        patientId: String,
        callback: (MedicalHistory?) -> Unit
    ) = remoteDataSource.getPatientMedicalHistory(patientId, callback)

    override suspend fun updatePatientMedicalHistory(
        patientId: String,
        medicalHistory: MedicalHistory
    ): Boolean = remoteDataSource.updatePatientMedicalHistory(patientId, medicalHistory)

    override suspend fun updatePatientChronicDiseases(
        patientId: String,
        chronicDiseases: List<String>
    ): Boolean = remoteDataSource.updatePatientChronicDiseases(patientId, chronicDiseases)

    override suspend fun updateEmergencyContacts(
        patientId: String,
        emergencyContacts: List<String>
    ): Boolean = remoteDataSource.updateEmergencyContacts(patientId, emergencyContacts)
    //endregion

}