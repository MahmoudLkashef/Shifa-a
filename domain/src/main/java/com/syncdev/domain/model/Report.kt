package com.syncdev.domain.model

data class Report(
    val id: String,
    val patientId: String,
    val doctorId: String,
    val medicines: List<Medication>,
    val advice: String
){
    // Add a no-argument constructor
    constructor(): this("","","", emptyList(),"")
}
