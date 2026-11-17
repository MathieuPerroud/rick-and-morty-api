package org.mathieu.cleanrmapi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay

/**
 * Hosts the application back stack with Navigation3.
 *
 * The single back handler of the application is the one installed by [NavDisplay] itself: it
 * calls [AppRouter.pop], which is guarded and therefore a no-op while only the root screen sits
 * at depth one. Each entry gets its own `ViewModelStore` so every screen builds its view model
 * per instance.
 */
@Composable
fun AppNavHost(
    scopeViewModel: AppScopeViewModel = viewModel()
) {
    val stack = scopeViewModel.stack

    NavDisplay(
        backStack = stack,
        onBack = scopeViewModel.router::pop,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = { key ->
            NavEntry(key) { key.MainComponent() }
        }
    )
}
