package com.syncdev.domain.model

data class Prescription(
    var id: String? = null,
    var patientId: String,
    var doctorId: String,
    val medicines: List<Medication>,
    val advice: String
){
    // Add a no-argument constructor
    constructor(): this("","","", emptyList(),"")
}
