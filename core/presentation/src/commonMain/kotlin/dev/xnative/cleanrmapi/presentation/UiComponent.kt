package dev.xnative.cleanrmapi.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Small UI unit contract used by both full screens and reusable components.
 */
interface UiComponent {
    /**
     * Primary rendering entry point for the component.
     */
    @Composable
    fun MainComponent(modifier: Modifier = Modifier)
}
