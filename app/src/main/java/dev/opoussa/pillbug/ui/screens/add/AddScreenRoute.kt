package dev.opoussa.pillbug.ui.screens.add

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
object AddRoute
@Composable
fun AddScreenRoute(navController: NavController) {
    val context = LocalContext.current
    val container = (context.applicationContext as PillbugApplication).container

    val factory = remember {
        AddViewModelFactory(container.medicationRepository)
    }
    val viewModel: AddViewModel = viewModel(factory = factory)

    AddScreen(viewModel = viewModel, navController = navController)

}