package dev.opoussa.pillbug.data.repository

import android.util.Log
import dev.opoussa.pillbug.data.local.dao.ConsumptionDao
import dev.opoussa.pillbug.data.local.entity.Consumption

class ConsumptionRepository(private val consumptionDao: ConsumptionDao) {
    suspend fun save(consumption: Consumption) {
        Log.d("REPO", "Saving consumption on ${consumption.timestamp}")
        consumptionDao.insert(consumption)
    }
    suspend fun delete(consumption: Consumption) {
        consumptionDao.delete(consumption)
    }
}