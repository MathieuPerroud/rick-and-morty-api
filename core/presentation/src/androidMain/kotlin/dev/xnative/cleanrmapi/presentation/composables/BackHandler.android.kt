package dev.xnative.cleanrmapi.presentation.composables

import androidx.activity.compose.BackHandler as AndroidBackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun BackHandler(onBack: () -> Unit) {
    AndroidBackHandler(onBack = onBack)
}