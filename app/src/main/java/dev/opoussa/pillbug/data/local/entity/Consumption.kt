package dev.opoussa.pillbug.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.sql.Time

@Serializable // For Type-Safe Navigation later
@Entity(
    tableName = "consumptions",
    foreignKeys = [
        ForeignKey(
            entity = Medication::class,
            parentColumns = ["id"],
            childColumns = ["medicationId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ]
)
data class Consumption(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val medicationId: Long,
    val amount: Int,
    val timestamp: Long = System.currentTimeMillis()
)
