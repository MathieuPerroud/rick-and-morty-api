package org.mathieu.cleanrmapi.ui.core.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import org.mathieu.cleanrmapi.ui.core.theme.OnBackgroundColor
import org.mathieu.cleanrmapi.ui.core.theme.SurfaceColor


@Composable
fun BackArrow(
    modifier: Modifier = Modifier,
    color: Color = OnBackgroundColor,
    onClick: () -> Unit
) = Image(
    modifier = modifier
        .padding(16.dp)
        .background(SurfaceColor, CircleShape)
        .padding(2.dp)
        .clickable(onClick = onClick),
    imageVector = Icons.Default.ArrowBack,
    contentDescription = "",
    colorFilter = ColorFilter.tint(color)
)
