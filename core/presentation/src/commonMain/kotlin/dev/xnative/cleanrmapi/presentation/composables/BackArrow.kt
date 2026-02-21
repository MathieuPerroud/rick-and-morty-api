package dev.xnative.cleanrmapi.presentation.composables

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
import dev.xnative.cleanrmapi.presentation.theme.OnBackgroundColor
import dev.xnative.cleanrmapi.presentation.theme.SurfaceColor

/**
 * Platform-neutral back button used in custom screen headers.
 *
 * Note: the icon is intentionally static here (`ArrowBack`). Platform-specific icon
 * differences are handled by dedicated toolbar components.
 */
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
