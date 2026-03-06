package dev.opoussa.pillbug.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable // For Type-Safe Navigation later
@Entity(tableName = "medications")
data class Medication (
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val name : String,
    val sizeMg : Int
)