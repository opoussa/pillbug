package dev.opoussa.pillbug.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.opoussa.pillbug.ui.components.MedWithConsumptionDTO
import dev.opoussa.pillbug.ui.util.getDateSuffix

@Composable
fun ListItem(
    medListItem: MedWithConsumptionDTO,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {

            /* ---------------------- */
            /* LEFT SIDE (2/3 WIDTH)  */
            /* ---------------------- */

            Column(
                modifier = Modifier
                    .weight(2f)
                    .background(colorScheme.primary)
                    .padding(20.dp)
            ) {
                Row() {
                    Text(
                        text = medListItem.medicationName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onPrimary
                    )
                    Spacer(modifier.width(8.dp))
                    Text(
                        text = medListItem.medicationSizeMg,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Light,
                        color = colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Medication",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onPrimary.copy(alpha = 0.8f)
                )
            }

            /* ---------------------- */
            /* RIGHT SIDE (1/3 WIDTH) */
            /* ---------------------- */

            val consumptionTime = medListItem.consumption?.time

            val day = consumptionTime?.dayOfMonth
            val monthYear = consumptionTime?.let {
                "${it.month.name.lowercase().replaceFirstChar { c -> c.uppercase() }} ${it.year}"
            } ?: "Not consumed"

            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(medListItem.color)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (day != null) {
                    Text(
                        text = "$day${getDateSuffix(day)}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onSecondary
                    )
                } else {
                    Text(
                        text = "--",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onSecondary
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = monthYear,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorScheme.onSecondary.copy(alpha = 0.9f)
                )
            }
        }
    }
}