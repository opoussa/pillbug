package dev.opoussa.pillbug.ui.screens.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.opoussa.pillbug.data.local.entity.Consumption
import dev.opoussa.pillbug.data.local.entity.Medication
import dev.opoussa.pillbug.data.repository.ConsumptionRepository
import dev.opoussa.pillbug.data.repository.MedicationRepository
import dev.opoussa.pillbug.ui.components.ConsumptionDTO
import dev.opoussa.pillbug.ui.components.MedWithConsumptionDTO
import dev.opoussa.pillbug.ui.components.createMedicationListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class DetailViewModel (
    private val args: DetailRoute,
    private val medRepo: MedicationRepository,
    private val consRepo: ConsumptionRepository
) : ViewModel() {

    val medicationId: Long = args.medicationId
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private var medication: Medication? = null
    private var consumption: Consumption? = null
    init {
        loadItem()
    }

    private fun loadItem() {
        Log.d("VIEWMODEL", "Load item!")
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading

            try {
                val entity = medRepo.getMedicationWithLatestConsumption(medicationId)
                medication = entity.medication
                consumption = entity.consumption

                val medicationDTO = createMedicationListItem(entity)
                val time  = medicationDTO.consumption?.time

                time?.dayOfYear?.let {
                    Log.d("DEBUG", "$it ${LocalDate.now().dayOfYear}")
                    if(it < LocalDate.now().dayOfYear) {
                        _uiState.value = DetailUiState.Success(article = medicationDTO, medConsumed = false)
                    } else {
                        _uiState.value = DetailUiState.Success(article = medicationDTO, medConsumed = true)
                    }
                }

                if(time == null) {
                    _uiState.value = DetailUiState.Success(article = medicationDTO, medConsumed = false)
                }

            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e)
            }
        }
    }

    // Add Consumption
    fun consumeMedication(medicationId: Long, amount: Int) {
        viewModelScope.launch {
            val newConsumption = Consumption(medicationId = medicationId, amount = amount)
            consRepo.save(newConsumption)
            loadItem()
        }
    }

    // Delete Consumption
    fun redactConsumption() {
        viewModelScope.launch {
            consumption?.let {
                consRepo.delete(it)
                consumption = null
                loadItem()
            }
        }
    }
    fun deleteMedication(medicationId: Long) {
        viewModelScope.launch {
            medication?.let {
                medRepo.deleteMedication(it)
                medication = null
                /* UI exits screen */
            }
        }
    }
}