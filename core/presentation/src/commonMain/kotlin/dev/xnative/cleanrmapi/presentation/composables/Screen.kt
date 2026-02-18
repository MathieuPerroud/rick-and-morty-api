package dev.xnative.cleanrmapi.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalFocusManager
import dev.xnative.cleanrmapi.navigation.Destination
import dev.xnative.cleanrmapi.navigation.Router
import dev.xnative.cleanrmapi.presentation.ViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun <State, VM: ViewModel<State>> Screen(
    viewModel: VM,
    router: Router,
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
                if (event is Destination) router.navigateTo(event)
                else onEvent(state, viewModel, event)
            }.collect()
    }

    content(state, viewModel)

}
