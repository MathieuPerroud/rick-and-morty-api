package dev.xnative.cleanrmapi.presentation.composables

import androidx.activity.compose.BackHandler as AndroidBackHandler
import androidx.compose.runtime.Composable

/**
 * Android implementation backed by `androidx.activity.compose.BackHandler`.
 */
@Composable
actual fun BackHandler(onBack: () -> Unit) {
    AndroidBackHandler(onBack = onBack)
}
