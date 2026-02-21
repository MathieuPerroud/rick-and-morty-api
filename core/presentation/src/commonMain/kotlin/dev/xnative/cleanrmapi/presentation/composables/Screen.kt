package dev.xnative.cleanrmapi.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalFocusManager
import dev.xnative.cleanrmapi.presentation.localproviders.LocalRouterProvider
import dev.xnative.cleanrmapi.navigation.Router
import dev.xnative.cleanrmapi.presentation.StateAwareViewModel
import dev.xnative.cleanrmapi.presentation.navigation.NavScreen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

/**
 * Binds a [StateAwareViewModel] to a composable screen.
 *
 * This wrapper centralizes:
 * - state collection,
 * - optional back handling,
 * - one-shot event processing (including navigation events).
 */
@Composable
fun <State, VM : StateAwareViewModel<State>> Screen(
    viewModel: VM,
    onBack: ((state: State, viewModel: VM) -> Unit)? = null,
    onEvent: (state: State, viewModel: VM, event: Any) -> Unit = { _, _, _ -> },
    content: @Composable (router: Router, state: State, viewModel: VM) -> Unit
) {
    val state by viewModel.state.collectAsState()

    val focusManager = LocalFocusManager.current
    val router = LocalRouterProvider.current

    if (onBack != null)
        BackHandler(
            onBack = {
                focusManager.clearFocus()
                onBack(state, viewModel)
            }
        )

    LaunchedEffect(viewModel) {
        viewModel.events
            .onEach { event ->
                if (event is NavScreen) router.navigateTo(event)
                else if (event is Exception) { /** Handle a notification */ }
                else onEvent(state, viewModel, event)
            }.collect()
    }

    content(router, state, viewModel)

}
