package dev.opoussa.pillbug.data.local.dao

import androidx.room.*

import dev.opoussa.pillbug.data.local.entity.Medication
import dev.opoussa.pillbug.data.local.entity.embedded.MedicationWithLatestConsumption
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicationDao {

    @Query("SELECT * FROM medications")
    suspend fun getAllMedications(): List<Medication>


    @Query("SELECT * FROM medications WHERE id = :id")
    suspend fun getById(id: Long): Medication?
    @Insert
    suspend fun insertMedication(medication: Medication) : Long

    @Query("""
        SELECT 
            m.id,
            m.name,
            m.sizeMg,
            c.id AS cons_id,
            c.medicationId AS cons_medicationId,
            c.amount AS cons_amount,
            c.timestamp AS cons_timestamp
        FROM medications m
        LEFT JOIN consumptions c
        ON c.id = (
            SELECT id
            FROM consumptions
            WHERE medicationId = m.id
            ORDER BY timestamp DESC
            LIMIT 1
        )
    """)
    fun getMedicationsWithLatestConsumption(): Flow<List<MedicationWithLatestConsumption>>

    @Query("""
        SELECT 
            m.id,
            m.name,
            m.sizeMg,
            c.id AS cons_id,
            c.medicationId AS cons_medicationId,
            c.amount AS cons_amount,
            c.timestamp AS cons_timestamp
        FROM medications m
        LEFT JOIN consumptions c
        ON c.id = (
            SELECT id
            FROM consumptions
            WHERE medicationId = m.id
            ORDER BY timestamp DESC
            LIMIT 1
            )
            WHERE m.id = :medId
    """)
    suspend fun getMedicationByIdWithLatestConsumption(medId: Long) : MedicationWithLatestConsumption

    @Delete
    suspend fun delete(medication: Medication)
}