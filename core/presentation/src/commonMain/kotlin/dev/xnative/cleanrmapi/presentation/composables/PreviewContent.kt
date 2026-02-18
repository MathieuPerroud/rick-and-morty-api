package dev.xnative.cleanrmapi.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun PreviewContent(content: @Composable BoxScope.() -> Unit) {
//    LeTheme {
        Box(modifier = Modifier.background(Color.White), content = content)
//    }
}

@Composable
fun PreviewContent(
    spacingVertical: Dp,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(spacingVertical),
    content: @Composable ColumnScope.() -> Unit
) {
//    LeTheme {
        Column(
            modifier = Modifier.background(Color.White),
            verticalArrangement = verticalArrangement,
            content = content
        )
//    }
}

@Composable
fun PreviewContent(
    spacingHorizontal: Dp,
    horizontalArrangement: Arrangement.Horizontal =  Arrangement.spacedBy(spacingHorizontal),
    content: @Composable RowScope.() -> Unit
) {
//    LeTheme {
        Row(
            modifier = Modifier.background(Color.White),
            horizontalArrangement = horizontalArrangement,
            content = content
        )
//    }
}