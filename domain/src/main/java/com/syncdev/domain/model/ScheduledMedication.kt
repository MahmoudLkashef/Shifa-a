package com.syncdev.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "scheduled_medications")
data class ScheduledMedication(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var type: String,
    var dosage: String,
    var startDate: String,
    var endDate: String,
    var frequency:String,
    var time: String,
    var scheduleLabels:List<String>
)
