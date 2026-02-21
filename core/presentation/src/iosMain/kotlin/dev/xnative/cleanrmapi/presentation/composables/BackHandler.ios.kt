package dev.xnative.cleanrmapi.presentation.composables

import androidx.compose.runtime.Composable

/**
 * iOS has no system "hardware back", so this is intentionally a no-op.
 */
@Composable
actual fun BackHandler(onBack: () -> Unit) {
}
