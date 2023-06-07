package com.syncdev.domain.model

/**
 * A data class representing a doctor.
 *
 * This data class holds information about a doctor such as their ID, first name, last name, gender, specialty,
 * phone number, and email address.
 *
 * @property id The unique identifier of the doctor.
 * @property firstName The first name of the doctor.
 * @property lastName The last name of the doctor.
 * @property gender The gender of the doctor.
 * @property speciality The specialty of the doctor.
 * @property phoneNumber The phone number of the doctor.
 * @property email The email address of the doctor.
 */
data class Doctor(
    var id: String? = null,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val speciality: String,
    val phoneNumber: String,
    val email: String
){
    // Add a no-argument constructor
    constructor() : this("", "", "", "", "", "", "")
}