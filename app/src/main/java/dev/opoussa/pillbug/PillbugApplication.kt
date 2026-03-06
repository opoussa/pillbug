package dev.opoussa.pillbug

import android.app.Application
import android.util.Log
import dev.opoussa.pillbug.di.AppContainer

class PillbugApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        Log.d("ONCREATE", "BuildConfig.USE_MOCK_DATA: ${BuildConfig.USE_MOCK_DATA}")
        container = AppContainer(context = this) // Application context

    }
}