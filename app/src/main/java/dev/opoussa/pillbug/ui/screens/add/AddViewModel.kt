package dev.opoussa.pillbug.ui.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.opoussa.pillbug.data.local.entity.Medication
import dev.opoussa.pillbug.data.repository.MedicationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddViewModel(
    private val medicationRepository: MedicationRepository
) : ViewModel() {

    fun addMedicine(name: String, sizeMg: String) {
        viewModelScope.launch {
            val sizeMgInt = Integer.valueOf(sizeMg)
            val newMedicine = Medication(name = name, sizeMg = sizeMgInt)

            medicationRepository.insertMedication(newMedicine)
        }
    }
}