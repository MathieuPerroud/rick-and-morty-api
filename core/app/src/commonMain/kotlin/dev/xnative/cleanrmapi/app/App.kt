package dev.xnative.cleanrmapi.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.xnative.cleanrmapi.presentation.navigation.NavScreen
import dev.xnative.cleanrmapi.presentation.localproviders.LocalRouterProvider

@Composable
fun App(
    appViewModel: AppViewModel = viewModel { AppViewModel() }
) {
    val state by appViewModel.state.collectAsState()

    CompositionLocalProvider(LocalRouterProvider provides state.router) {
        NavDisplay(
            backStack = state.backStack,
            onBack = state.router::navigateBack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = { key ->
                NavEntry(key) {
                    when (key) {
                        is NavScreen -> key.MainComponent()
                        else -> Splash.Screen()
                    }
                }
            }
        )
    }
}
