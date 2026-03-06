package dev.opoussa.pillbug.ui.components


import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val title = when (currentRoute?.substringBefore("/")) { // TODO : Fix for non-string, typesafe nav.
        "list" -> "Cabinet"
        "add" -> "Add"
        "details" -> "Details"
        else -> "Pillbug"
    }
    CenterAlignedTopAppBar(
        title = { Text(title, fontWeight = FontWeight.Bold) }
    )
}
