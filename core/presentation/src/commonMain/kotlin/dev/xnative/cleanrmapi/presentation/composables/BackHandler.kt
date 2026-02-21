package dev.xnative.cleanrmapi.presentation.composables

import androidx.compose.runtime.Composable

/**
 * Registers a back callback on platforms that expose one.
 *
 * On targets without system back support, the `actual` implementation is a no-op.
 */
@Composable
expect fun BackHandler(onBack: () -> Unit)
