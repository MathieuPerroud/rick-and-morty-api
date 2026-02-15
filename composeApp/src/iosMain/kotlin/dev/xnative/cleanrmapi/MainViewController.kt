package dev.xnative.cleanrmapi

import androidx.compose.ui.window.ComposeUIViewController
import dev.xnative.cleanrmapi.ui.App

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}