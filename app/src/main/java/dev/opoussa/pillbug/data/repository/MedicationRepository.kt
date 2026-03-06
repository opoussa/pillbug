package dev.opoussa.pillbug.data.repository

import android.util.Log
import dev.opoussa.pillbug.data.local.dao.MedicationDao
import dev.opoussa.pillbug.data.local.entity.Medication
import dev.opoussa.pillbug.data.local.entity.embedded.MedicationWithLatestConsumption
import kotlinx.coroutines.flow.Flow

class MedicationRepository(private val medicationDao: MedicationDao) {
    suspend fun getAllMedications() = medicationDao.getAllMedications()

    suspend fun getMedication(id: Long) = medicationDao.getById(id)
    suspend fun getMedicationWithLatestConsumption(id: Long): MedicationWithLatestConsumption {
        Log.d("REPO", "Getting medication with consumption")
        return medicationDao.getMedicationByIdWithLatestConsumption(id)
    }
    suspend fun insertMedication(medication: Medication) =
        medicationDao.insertMedication(medication)

    fun getMedicationsWithLatestConsumption(): Flow<List<MedicationWithLatestConsumption>> {
        return medicationDao.getMedicationsWithLatestConsumption()
    }

    suspend fun deleteMedication(medication: Medication) {
        medicationDao.delete(medication)
    }
}