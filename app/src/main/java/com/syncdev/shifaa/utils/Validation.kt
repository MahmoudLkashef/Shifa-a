package com.syncdev.shifaa.utils

import android.os.Build
import android.util.Patterns
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputLayout

object Validation {

    fun isEmptyTextInput(text: String, view: TextInputLayout): Boolean{
        return if (text.isEmpty()){
            view.error = "This field cannot be empty"
            false
        }else{
            true
        }
    }
    fun validateName(name: String, view: TextInputLayout): Boolean{
        return if (name.isEmpty()){
            view.error = "This field is required"
            false
        }else{
            true
        }
    }

    fun validateDay(day: String, view: TextInputLayout): Boolean{
        return if (day.isEmpty()){
            false
        }else if (!DateUtils.get31DayList().contains(day)){
            view.error = "Select day"
            false
        }else{
            view.error = null
            true
        }
    }

    fun validateMonth(selectedMonth: String, month: Array<out String>,view: TextInputLayout):Boolean{
        return if (selectedMonth.isEmpty()){
            false
        }else if (!month.contains(selectedMonth)){
            view.error = "Select month"
            false
        }else{
            view.error = null
            true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun validateYear(year: String, view: TextInputLayout): Boolean{
        return if (year.isEmpty()){
            false
        }else if (!DateUtils.getLast100Years().contains(year)){
            view.error = "Select year"
            false
        }else{
            true
        }
    }

    fun validateEmail(email: String,view: TextInputLayout): Boolean{
        val emailPattern = Patterns.EMAIL_ADDRESS
        return if (email.isEmpty()){
            false
        }else if (!emailPattern.matcher(email).matches()) {
            view.error = "Please enter a valid email address"
            false
        }else {
            view.error = null
            true
        }
    }

    fun validatePhoneNumber(phoneNumber: String, view: TextInputLayout): Boolean{
        val phoneNumberPattern = "^01[0-2]{1}[0-9]{8}$".toRegex()
        return if (phoneNumber.isEmpty()){
            false
        }else if (!phoneNumber.matches(phoneNumberPattern)) {
            view.error = "Please enter a valid phone number"
            false
        } else {
            view.error = null
            true
        }
    }

    fun validatePassword(password: String, view: TextInputLayout): Boolean{
        return if (password.isEmpty()) {
            false
        }else if (password.length !in 8..16) {
            view.error = "Password should be between 8 and 16 characters"
            false
        }else {
            view.error = null
            true
        }
    }

    fun validateConfirmPassword(confirmPassword: String , password: String?, view: TextInputLayout): Boolean{
        return if (confirmPassword.isEmpty()){
            false
        }else if (confirmPassword != password) {
            view.error = "Passwords do not match"
            false
        } else {
            view.error = null
            true
        }
    }

    fun validateSpeciality(speciality: String, specialities: Array<out String>,view: TextInputLayout):Boolean{
        return if (speciality.isEmpty()){
            false
        }else if (!specialities.contains(speciality)){
            view.error = "Select speciality"
            false
        }else{
            view.error = null
            true
        }
    }

}