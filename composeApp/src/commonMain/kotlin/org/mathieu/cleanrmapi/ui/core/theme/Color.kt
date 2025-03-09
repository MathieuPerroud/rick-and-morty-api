package org.mathieu.cleanrmapi.ui.core.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val BackgroundColor: Color
    @Composable get() = MaterialTheme.colors.background
val OnBackgroundColor: Color
    @Composable get() = MaterialTheme.colors.onBackground
val PrimaryColor: Color
    @Composable get() = MaterialTheme.colors.primary
val OnPrimaryColor: Color
    @Composable get() = MaterialTheme.colors.onPrimary
val SecondaryColor: Color
    @Composable get() = MaterialTheme.colors.secondary

val SurfaceColor: Color
    @Composable get() = MaterialTheme.colors.surface