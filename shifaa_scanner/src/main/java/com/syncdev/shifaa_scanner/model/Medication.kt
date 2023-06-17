package com.syncdev.shifaa_scanner.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Medication(
    val id: String,
    var name: String,
    var type: String,
    var period: String,
    var dosage: String,
    var frequency:String,
    var scheduleLabels:List<String>
): Parcelable {
    // Add a no-argument constructor
    constructor(): this("","","","","","", emptyList())
}
