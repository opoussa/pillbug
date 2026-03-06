package dev.opoussa.pillbug.ui.screens.detail


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import dev.opoussa.pillbug.ui.components.ErrorComposable
import dev.opoussa.pillbug.ui.util.getDateSuffix

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    navController: NavController,
    state: DetailUiState
) {
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    if(showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
        ) {

            Text(
                "Redact last consumption",
                color = Color.Blue,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
                    .clickable {
                        if (state is DetailUiState.Success) {
                            viewModel.redactConsumption()
                            // TODO : Toast "Last consumption redacted"
                            showSheet = false
                        }
                    }
            )


//                Icon(Icons.Default.Delete, "Delete")
            Text(
                "Delete medication",
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
                    .clickable {
                        viewModel.deleteMedication(viewModel.medicationId)
                        navController.navigateUp()
                    }
            )

        }

    }
    Box(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Text(
                text = "← Back to cabinet",
                style = typography.bodyLarge,
                color = colorScheme.primary,
                modifier = Modifier
                    .clickable { navController.navigateUp() }
                    .padding(vertical = 12.dp)
            )

            Spacer(Modifier.height(12.dp))


            when (state) {

                is DetailUiState.Loading -> CircularProgressIndicator(
                    Modifier
                        .fillMaxSize()
                        .padding(30.dp)
                )

                is DetailUiState.Error -> ErrorComposable(
                    text = "Error: $state",
                    color = Color.Red
                )

                is DetailUiState.Success -> Box() {

                    val name = state.article.medicationName
                    val size = state.article.medicationSizeMg
                    Column(
                        Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    ) {
                        // Title
                        Row() {
                            Text(
                                text = name,
                                style = typography.headlineLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "($size)",
                                style = typography.headlineLarge
                            )


                            IconButton(onClick = { showSheet = true }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                            }
                        }

                        Spacer(Modifier.height(4.dp))

                        Spacer(Modifier.height(8.dp))

                        Text("Last consumed on:")
                        Spacer(Modifier.height(18.dp))

                        when (state.article.consumption) {
                            null -> {
                                Text("Never")
                            }

                            else -> {
                                val day: Int = state.article.consumption.time.dayOfMonth
                                val dayString = "${day} ${getDateSuffix(day)}"
                                val month = state.article.consumption.time.month.name
                                val year: Int = state.article.consumption.time.year
                                val color = state.article.color
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp),
                                    color = state.article.color.copy(alpha = 0.15f)
                                ) {
                                    Column(
                                        Modifier.padding(20.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            dayString,
                                            style = typography.displaySmall,
                                            fontWeight = FontWeight.Bold,
                                            color = state.article.color
                                        )

                                        Text(
                                            "$month $year",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                }

                            }
                        }

                        Spacer(Modifier.weight(1f))
                        if (state.medConsumed) {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults
                                    .cardElevation(defaultElevation = 6.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp)
                                    .wrapContentSize(Alignment.Center, false)
                            ) {
                                Text(
                                    "$name consumed ✓",
                                    color = state.article.color,
                                    style = typography.titleMedium,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        } else {
                            FloatingActionButton(
                                onClick = {
                                    val id = state.article.medicationId
                                    viewModel.consumeMedication(medicationId = id, amount = 1)
                                },
                                containerColor = colorScheme.secondary,
                                contentColor = colorScheme.onSecondary,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(30.dp)
                            ) {
                                Text(
                                    text = "Consume $name",
                                    style = typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}

