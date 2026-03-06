package dev.opoussa.pillbug.di

import android.content.Context
import dev.opoussa.pillbug.data.local.PillbugDatabase
import dev.opoussa.pillbug.data.repository.ConsumptionRepository
import dev.opoussa.pillbug.data.repository.MedicationRepository

class AppContainer(context: Context) {
    // Database
    private val database = PillbugDatabase.getDatabase(context)

    private val medicationDao = database.medicationDao()
    private val consumptionDao = database.consumptionDao()
    // database.scheduleDao

    val medicationRepository = MedicationRepository(medicationDao)
    val consumptionRepository = ConsumptionRepository(consumptionDao)
}