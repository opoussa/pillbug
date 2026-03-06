package dev.opoussa.pillbug.ui.screens.list

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
object ListRoute
@Composable
fun ListScreenRoute(navController: NavController) {
    val context = LocalContext.current
    val container = (context.applicationContext as PillbugApplication).container

    val factory = remember {
        ListViewModelFactory(container.medicationRepository)
    }

    val viewModel: ListViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ListScreen(
        uiState = uiState,
        navController = navController
    )
}