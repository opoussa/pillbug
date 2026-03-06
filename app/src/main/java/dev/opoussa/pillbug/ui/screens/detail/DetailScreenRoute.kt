package dev.opoussa.pillbug.ui.screens.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.opoussa.pillbug.PillbugApplication
import kotlinx.serialization.Serializable

@Serializable
data class DetailRoute(
    val medicationId: Long
)
@Composable
fun DetailScreenRoute(navController: NavController, args: DetailRoute) {

    val context = LocalContext.current
    val container = (context.applicationContext as PillbugApplication).container
    val factory = remember {
        DetailViewModelFactory(
            args,
            medicationRepository = container.medicationRepository,
            consumptionRepository = container.consumptionRepository
        )
    }
    val viewModel: DetailViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DetailScreen(viewModel, navController, uiState)
}