package dev.xnative.cleanrmapi.ui.core.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import dev.xnative.cleanrmapi.ui.core.ViewModel

@Composable
fun <State, VM: ViewModel<State>> Screen(
    viewModel: VM,
    backStack: NavBackStack<NavKey>,
    onBack: ((state: State, viewModel: VM) -> Unit)? = null,
    onEvent: (state: State, viewModel: VM, event: Any) -> Unit = { _, _, _ ->  },
    content: @Composable (state: State, viewModel: VM) -> Unit
) {

    // Observing the state from the view model.
    val state by viewModel.state.collectAsState()

    val focusManager = LocalFocusManager.current

    // Handle back press event.
    if (onBack != null)
        BackHandler(
            onBack = {
                focusManager.clearFocus()
                onBack(state, viewModel)
            }
        )

    // Collect events emitted by the ViewModel.
    LaunchedEffect(viewModel) {
        viewModel.events
            .onEach { event ->
                if (event is NavKey) backStack.add(event)
                else onEvent(state, viewModel, event)
            }.collect()
    }

    content(state, viewModel)

}

@Composable
expect fun BackHandler(onBack: () -> Unit)