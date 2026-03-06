package dev.opoussa.pillbug.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.opoussa.pillbug.ui.components.ErrorComposable
import dev.opoussa.pillbug.ui.screens.add.AddRoute
import dev.opoussa.pillbug.ui.screens.detail.DetailRoute

@Composable
fun ListScreen(
    uiState: ListUiState,
    navController: NavController
) {
    val colorScheme = MaterialTheme.colorScheme

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {

            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        color = colorScheme.primary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.error != null -> {
                    ErrorComposable(
                        text = "Error: ${uiState.error}",
                        color = colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.items.isEmpty() -> {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Text("Welcome to your medicine cabinet!", style = typography.bodyLarge)
                        Spacer(modifier = Modifier.size(width = 0.dp, height = 10.dp))
                        Text("↓ Tap the button below to add medicine! ↓")
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {
                        items(uiState.items) { item ->
                            ListItem(
                                medListItem = item,
                                modifier = Modifier.clickable {
                                    navController.navigate(DetailRoute(item.medicationId))
                                }
                            )
                        }
                    }
                }
            }

            // Floating Add Button
            FloatingActionButton(
                onClick = { navController.navigate(AddRoute) },
                containerColor = colorScheme.secondary,
                contentColor = colorScheme.onSecondary,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxWidth()
                    .padding(30.dp)
            ) {
                Text(
                    text = "Add medicine",
                    style = typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onPrimary
                )
            }
        }
    }
}
