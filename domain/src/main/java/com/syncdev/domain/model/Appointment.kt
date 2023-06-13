package com.syncdev.domain.model

data class Appointment(
    var id: String? = null,
    val patient: Patient,
    val doctor: Doctor,
    val time: String,
    val date: String,
    val comment: String,
    var state: String? = null,
    var report: Report? = null
){
    // Add a no-argument constructor
    constructor(): this("", Patient(), Doctor(),"","","","",Report())
}
