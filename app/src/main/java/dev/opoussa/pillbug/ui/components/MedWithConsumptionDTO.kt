package dev.opoussa.pillbug.ui.components

import androidx.compose.ui.graphics.Color
import dev.opoussa.pillbug.data.local.entity.embedded.MedicationWithLatestConsumption
import dev.opoussa.pillbug.ui.theme.NotiYellow
import dev.opoussa.pillbug.ui.theme.OkGreen
import dev.opoussa.pillbug.ui.theme.WarnOrange
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

data class ConsumptionDTO(
    val id: Long,
    val time : LocalDateTime,
)
data class MedWithConsumptionDTO(
    val medicationId: Long,
    val medicationName: String,
    val medicationSizeMg: String,
    val consumption: ConsumptionDTO?,
    val color: Color
)

fun createMedicationListItem(entity: MedicationWithLatestConsumption): MedWithConsumptionDTO {

    val med = entity.medication
    val cons = entity.consumption

    var consumptionTimeLocal: LocalDateTime? = null
    var itemColor = OkGreen
    var consDto: ConsumptionDTO? = null

    if (cons != null) {
        consumptionTimeLocal = LocalDateTime.ofEpochSecond(
            cons.timestamp / 1000,
            0,
            ZoneOffset.UTC
        )
        consDto = ConsumptionDTO(
            cons.id,
            consumptionTimeLocal
        )
        itemColor = when {
            consumptionTimeLocal.toLocalDate() == LocalDate.now().minusDays(1) -> NotiYellow
            consumptionTimeLocal.toLocalDate() < LocalDate.now().minusDays(1) -> WarnOrange
            else -> itemColor
        }
    }
    return MedWithConsumptionDTO(
        medicationId = med.id,
        medicationName = med.name,
        medicationSizeMg = "${med.sizeMg}mg",
        consumption = consDto,
        color = itemColor
    )
}