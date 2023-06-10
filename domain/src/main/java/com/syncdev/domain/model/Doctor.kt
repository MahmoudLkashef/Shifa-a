package com.syncdev.domain.model

/**
 * A data class representing a doctor.
 *
 * This data class holds information about a doctor such as their ID, first name, last name, gender, specialty,
 * phone number, email address, years of experience, total rating, number of reviews, about doctor, and number of patients.
 *
 * @property id The unique identifier of the doctor.
 * @property firstName The first name of the doctor.
 * @property lastName The last name of the doctor.
 * @property gender The gender of the doctor.
 * @property speciality The specialty of the doctor.
 * @property phoneNumber The phone number of the doctor.
 * @property email The email address of the doctor.
 * @property yearsOfExperience The years of experience of the doctor. Default value is 0.
 * @property totalRating The total rating of the doctor. Default value is 0.0.
 * @property numOfReviews The number of reviews for the doctor. Default value is 0.
 * @property aboutDoctor The information about the doctor.
 * @property numOfPatients The number of patients associated with the doctor. Default value is 0.
 */
data class Doctor(
    var id: String? = null,
    var firstName: String,
    var lastName: String,
    val gender: String,
    var speciality: String,
    var phoneNumber: String,
    var email: String,
    var yearsOfExperience: Int = 0,
    var totalRating: Float = 0f,
    var numOfReviews: Int = 0,
    var aboutDoctor: String = "",
    var numOfPatients: Int = 0
) {
    // Add a no-argument constructor
    constructor() : this("", "", "", "", "", "", "", 0, 0f, 0, "", 0)
}
