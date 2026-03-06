package dev.opoussa.pillbug.ui.screens.add

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults.containerColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AddScreen(
    viewModel: AddViewModel,
    navController: NavController
) {
    val colorScheme = MaterialTheme.colorScheme
    val context = LocalContext.current

    var nameInput by remember { mutableStateOf("") }
    var sizeMgInput by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            // Back Navigation
            Text(
                text = "← Back to cabinet",
                style = typography.bodyLarge,
                color = colorScheme.primary,
                modifier = Modifier
                    .clickable { navController.popBackStack() }
                    .padding(vertical = 12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Title
            Text(
                text = "Add medicine",
                style = typography.headlineLarge,
                color = colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Medication Name Field
            OutlinedTextField(
                value = nameInput,
                onValueChange = { nameInput = it },
                label = { Text("Medication name") },
                placeholder = { Text("Enter medication name...") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dose Field
            OutlinedTextField(
                value = sizeMgInput,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        sizeMgInput = newValue
                    }
                },
                label = { Text("Dose (mg)") },
                placeholder = { Text("Enter dose in mg...") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Add Button
            FloatingActionButton(
                onClick = {
                    if (validateForm(nameInput, sizeMgInput)) {
                        viewModel.addMedicine(nameInput, sizeMgInput)

                        Toast.makeText(
                            context,
                            "Medication added to cabinet",
                            Toast.LENGTH_SHORT
                        ).show()

                        navController.navigateUp()
                    }
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Add to cabinet",
                    style = typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )

            }

        }
    }
}

private fun validateForm(name: String, size: String): Boolean {
    return !(name == "" || size == "" || size == "0" || size.all {!it.isDigit()})
}