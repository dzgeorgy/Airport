package ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun AirportTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colors = lightColors(
            primary = deepBlue,
            surface = lightBlue,
            secondary = blue,
            secondaryVariant = yellow
        ),
        content = content
    )

}