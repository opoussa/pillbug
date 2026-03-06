package dev.opoussa.pillbug.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Overload without modifier
 */
@Composable
fun ErrorComposable(text: String, color: Color) {
    ErrorComposable(text, color, Modifier)
}
@Composable
fun ErrorComposable(text: String, color: Color, modifier: Modifier){
    Box(modifier = modifier
        .padding(3.dp)
    ) {
        Text(
            text = text,
            modifier = modifier
                .padding(5.dp),
            color = color
        )
    }
}