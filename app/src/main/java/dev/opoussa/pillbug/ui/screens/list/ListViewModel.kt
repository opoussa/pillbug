package dev.opoussa.pillbug.ui.screens.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.opoussa.pillbug.data.repository.MedicationRepository
import dev.opoussa.pillbug.ui.components.MedWithConsumptionDTO
import dev.opoussa.pillbug.ui.components.createMedicationListItem
import dev.opoussa.pillbug.ui.theme.NotiYellow
import dev.opoussa.pillbug.ui.theme.OkGreen
import dev.opoussa.pillbug.ui.theme.WarnOrange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

class ListViewModel(private val medicationRepository: MedicationRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ListUiState(isLoading = true, items = emptyList()))
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            medicationRepository
                .getMedicationsWithLatestConsumption()
                .collect { entityPairs ->

                    val items = entityPairs.map { relation ->
                        createMedicationListItem(relation)
                    }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            items = items,
                            error = null
                        )
                    }
                }
        }
    }
}
