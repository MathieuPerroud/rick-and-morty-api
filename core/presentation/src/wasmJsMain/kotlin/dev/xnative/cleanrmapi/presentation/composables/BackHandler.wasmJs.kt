package dev.xnative.cleanrmapi.presentation.composables

import androidx.compose.runtime.Composable

/**
 * Wasm/Web target: no system back bridge is implemented at this layer.
 */
@Composable
actual fun BackHandler(onBack: () -> Unit) {
}
