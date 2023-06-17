package com.syncdev.domain.model

data class MedicalHistory(
     var id:String?=null,
     var bloodType:String,
     var age:String,
     var height:String,
     var weight:String,
     var chronicDiseases:List<String>,
     var medication:List<Medication>
){
    constructor(): this("","","","","", emptyList(), emptyList())
}
