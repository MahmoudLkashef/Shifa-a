package com.syncdev.shifaa_scanner.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicalHistory(
    var id:String?=null,
    var bloodType:String,
    var age:String,
    var height:String,
    var weight:String,
    var chronicDiseases:List<String>,
    var medication:List<Medication>,
    var emergencyContacts: List<String> = emptyList(),
    var patientName:String=""
): Parcelable {
    constructor(): this("","","","","", emptyList(), emptyList(), emptyList(),"")
}
