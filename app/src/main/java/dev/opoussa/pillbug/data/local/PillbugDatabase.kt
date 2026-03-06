package dev.opoussa.pillbug.data.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.opoussa.pillbug.BuildConfig
import dev.opoussa.pillbug.data.local.dao.ConsumptionDao
import dev.opoussa.pillbug.data.local.dao.MedicationDao
import dev.opoussa.pillbug.data.local.entity.Consumption
import dev.opoussa.pillbug.data.local.entity.Medication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Medication::class, Consumption::class],
    version = 1,
    exportSchema = true
)
abstract class PillbugDatabase : RoomDatabase() {

    abstract fun medicationDao(): MedicationDao
    abstract fun consumptionDao(): ConsumptionDao

    companion object {
        @Volatile
        private var INSTANCE: PillbugDatabase? = null

        fun getDatabase(context: Context): PillbugDatabase {
            return INSTANCE ?: synchronized(this) {
                lateinit var database: PillbugDatabase
                database = Room.databaseBuilder(
                    context,
                    PillbugDatabase::class.java,
                    "pillbug.db"
                )
                .addCallback(DebugPrepopulateCallback { database })
                .build()

                INSTANCE = database

                return database
            }
        }

        /***********************************
            Pre-populate database in debug mode:
         */
        private suspend fun prepopulateDebugData(database: PillbugDatabase) {

            Log.d("INFO","Populating Database ...")
            val medDao = database.medicationDao()
            val consDao = database.consumptionDao()

            val med1 = Medication(name = "Aspirin", sizeMg = 100)
            val med2 = Medication(name = "Ibuprofen", sizeMg = 200)
            val medIds = listOf(med1, med2).map { medDao.insertMedication(it) }

            val cons1 = Consumption(medicationId = medIds[0], amount = 1)
            val cons2 = Consumption(medicationId = medIds[1], amount = 2)
            consDao.insert(cons1)
            consDao.insert(cons2)
            Log.d("INFO","Database populated.")
        }

        // Callback to run prepopulation
        private class DebugPrepopulateCallback(
            private val dbProvider: () -> PillbugDatabase
        ) : Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d("DB","onCreate fired.")
                if (BuildConfig.USE_MOCK_DATA) {
                    CoroutineScope(Dispatchers.IO).launch {
                        prepopulateDebugData(dbProvider())
                    }
                }
            }
        }

    }
}