package dev.opoussa.pillbug

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.opoussa.pillbug.ui.components.AppBar
import dev.opoussa.pillbug.ui.screens.add.AddRoute
import dev.opoussa.pillbug.ui.screens.add.AddScreenRoute
import dev.opoussa.pillbug.ui.screens.detail.DetailRoute
import dev.opoussa.pillbug.ui.screens.detail.DetailScreenRoute
import dev.opoussa.pillbug.ui.screens.list.ListRoute
import dev.opoussa.pillbug.ui.screens.list.ListScreenRoute
import dev.opoussa.pillbug.ui.theme.PillbugTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PillbugApp()
        }
    }
}

@Preview
@Composable
fun PillbugApp() {
    val navController = rememberNavController()

    PillbugTheme {
        Scaffold(
            topBar = {
                AppBar(navController)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = ListRoute,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable<ListRoute> {
                    ListScreenRoute(navController)
                }

                composable<AddRoute> {
                    AddScreenRoute(navController)
                }

                composable<DetailRoute> {
                    val args = it.toRoute<DetailRoute>()
                    DetailScreenRoute(navController, args)
                }
            }
        }
    }
}