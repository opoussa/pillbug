package dev.opoussa.pillbug.ui.screens.list

import dev.opoussa.pillbug.ui.components.MedWithConsumptionDTO

data class ListUiState(
    var isLoading: Boolean,
    var error: Exception? = null,
    var items: List<MedWithConsumptionDTO>
)
