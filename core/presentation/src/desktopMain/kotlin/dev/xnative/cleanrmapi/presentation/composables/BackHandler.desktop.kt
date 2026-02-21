package dev.xnative.cleanrmapi.presentation.composables

import androidx.compose.runtime.Composable

/**
 * Desktop target: no platform-level back callback is wired for now.
 */
@Composable
actual fun BackHandler(onBack: () -> Unit) {
}
