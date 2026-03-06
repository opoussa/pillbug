package dev.opoussa.pillbug.data.local.entity.embedded

import androidx.room.Embedded
import dev.opoussa.pillbug.data.local.entity.Consumption
import dev.opoussa.pillbug.data.local.entity.Medication

data class MedicationWithLatestConsumption(
    @Embedded val medication: Medication,
    @Embedded(prefix = "cons_") val consumption: Consumption?
)