package com.syncdev.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syncdev.domain.model.ScheduledMedication

@Dao
interface ScheduledMedicationDao {
    @Query("SELECT * FROM scheduled_medications")
    suspend fun getAllScheduledMedications(): List<ScheduledMedication>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScheduledMedication(scheduledMedication: ScheduledMedication)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScheduledMedications(scheduledMedications: List<ScheduledMedication>)

    // Add other necessary database operations (update, delete, etc.)
}