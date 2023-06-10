package com.syncdev.domain.model

data class AppointmentRequest(
    val patientId: String,
    val doctorId: String,
    val date: String,
    val time: String,
    val comment: String
){
    // Add a no-argument constructor
    constructor() : this("","","","","")
}