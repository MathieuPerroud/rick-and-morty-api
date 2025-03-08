package org.mathieu.cleanrmapi.ui.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val BackgroundColor: Color
    @Composable get() = MaterialTheme.colorScheme.background
val OnBackgroundColor: Color
    @Composable get() = MaterialTheme.colorScheme.onBackground
val PrimaryColor: Color
    @Composable get() = MaterialTheme.colorScheme.primary
val OnPrimaryColor: Color
    @Composable get() = MaterialTheme.colorScheme.onPrimary
val SecondaryColor: Color
    @Composable get() = MaterialTheme.colorScheme.secondary

val SurfaceColor: Color
    @Composable get() = MaterialTheme.colorScheme.surface