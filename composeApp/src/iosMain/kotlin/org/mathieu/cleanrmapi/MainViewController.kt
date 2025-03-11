package org.mathieu.cleanrmapi

import androidx.compose.ui.window.ComposeUIViewController
import org.mathieu.cleanrmapi.ui.App

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}