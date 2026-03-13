package dev.opoussa.pillbug.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun YesNoDialog(
    title: String,
    message: String,
    onYes: () -> Unit,
    onNo: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onNo,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onYes) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onNo) {
                Text("No")
            }
        }
    )
}