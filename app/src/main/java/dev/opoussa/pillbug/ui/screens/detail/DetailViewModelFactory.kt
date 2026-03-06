package dev.opoussa.pillbug.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.opoussa.pillbug.data.repository.ConsumptionRepository
import dev.opoussa.pillbug.data.repository.MedicationRepository

/**
 * Boiler? I barely know her :-DDDD
 */
class DetailViewModelFactory (
    private val args: DetailRoute,
    private val medicationRepository: MedicationRepository,
    private val consumptionRepository: ConsumptionRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(
                args,
                medRepo = medicationRepository,
                consRepo = consumptionRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}