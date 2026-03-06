package dev.opoussa.pillbug.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.opoussa.pillbug.data.repository.MedicationRepository

/**
 *   I thought boilerplate was a Java thing.
 */
class ListViewModelFactory(
    private val medicationRepository: MedicationRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(medicationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}