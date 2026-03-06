package dev.opoussa.pillbug.ui.screens.detail

import dev.opoussa.pillbug.ui.components.MedWithConsumptionDTO

sealed interface DetailUiState {
    object Loading: DetailUiState
    data class Success(val article: MedWithConsumptionDTO, val medConsumed: Boolean) : DetailUiState
    data class Error(val error: Exception) : DetailUiState
}