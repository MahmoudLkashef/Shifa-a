package com.syncdev.domain.model

/**
 * A data class representing a patient.
 *
 * This data class holds information about a patient such as their ID, first name, last name, age, gender,
 * phone number, and email address.
 *
 * @property id The unique identifier of the patient.
 * @property firstName The first name of the patient.
 * @property lastName The last name of the patient.
 * @property age The age of the patient.
 * @property gender The gender of the patient.
 * @property phoneNumber The phone number of the patient.
 * @property email The email address of the patient.
 */
data class Patient(
    var id: String,
    val firstName: String,
    val lastName: String,
    val age: String,
    val gender: String,
    val phoneNumber: String,
    val email: String
)

