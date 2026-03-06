package dev.opoussa.pillbug.ui.screens.add
data class AddFormEntry(
    var medicationName: String,
    var medicationSizeMg: Int
)
data class AddUiState(
    var isLoading: Boolean,
    var formEntry: AddFormEntry
)
