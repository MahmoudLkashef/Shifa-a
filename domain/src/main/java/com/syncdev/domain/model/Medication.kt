package com.syncdev.domain.model

data class Medication(
    val id: String,
    var name: String,
    var type: String,
    var period: String,
    var dosage: String
){
    // Add a no-argument constructor
    constructor(): this("","","","","")
}
