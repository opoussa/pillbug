package dev.opoussa.pillbug.ui.util

fun getDateSuffix(dayOfMonth: Int): String {
    return if(dayOfMonth == 1) {
        "st"
    } else if(dayOfMonth == 2) {
        "nd"
    } else {
        "th"
    }
}

