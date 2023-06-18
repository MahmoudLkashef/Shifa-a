package com.syncdev.domain.model

data class MedicalHistory(
     var id:String?=null,
     var bloodType:String,
     var age:String,
     var height:String,
     var weight:String,
     var chronicDiseases:List<String>,
     var medication:List<Medication>,
     var emergencyContacts: List<String> = emptyList()
){
    constructor(): this("","","","","", emptyList(), emptyList(), emptyList())
}
