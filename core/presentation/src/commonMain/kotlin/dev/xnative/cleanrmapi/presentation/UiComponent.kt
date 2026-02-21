package dev.xnative.cleanrmapi.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface UiComponent {
    @Composable
    fun MainComponent(modifier: Modifier = Modifier)
}