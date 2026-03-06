// pillbug/build.gradle.kts (Root folder)
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.serialization) apply false

    // Use the corrected alias here
    alias(libs.plugins.androidx.room) apply false
}