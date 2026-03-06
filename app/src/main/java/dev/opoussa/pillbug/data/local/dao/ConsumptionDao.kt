package dev.opoussa.pillbug.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.opoussa.pillbug.data.local.entity.Consumption

@Dao
interface ConsumptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(consumption: Consumption): Long
    @Delete
    suspend fun delete(consumption: Consumption)

    @Query("""
        SELECT * FROM consumptions 
            WHERE medicationId = :medicationId 
            ORDER BY timestamp DESC LIMIT 1
        """)
    suspend fun getLatestConsumptionByMedicationId(medicationId : Long): Consumption?
}