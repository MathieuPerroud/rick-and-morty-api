package dev.xnative.cleanrmapi

import androidx.compose.ui.window.ComposeUIViewController
import dev.xnative.cleanrmapi.app.App
import dev.xnative.cleanrmapi.app.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}