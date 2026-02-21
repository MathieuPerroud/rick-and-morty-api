package dev.xnative.cleanrmapi.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import dev.xnative.cleanrmapi.app.router.AppRouter
import dev.xnative.cleanrmapi.characters.navigation.CharactersNavigation
import dev.xnative.cleanrmapi.navigation.Router
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

/**
 * Contracts owned by [AppViewModel].
 */
interface AppContracts {
    /**
     * Holds app-level navigation state and the router bound to it.
     */
    data class UiState(
//        val graphs: List<Graph> = emptyList(),
        val backStack: NavBackStack<NavKey> = NavBackStack(Splash),
        val router: Router = AppRouter(backStack)
    )
}

/**
 * Initializes app-wide navigation and exposes the root back stack.
 *
 * Technical choices:
 * - Router is created from state and injected dynamically in Koin so feature modules
 *   can navigate without depending on Compose.
 * - Splash is pushed first, then replaced by the initial feature entry point.
 */
class AppViewModel :
    ViewModel(),
    KoinComponent {

    private val _state = MutableStateFlow(AppContracts.UiState())
    val state: StateFlow<AppContracts.UiState>
        get() = _state

//    private val charactersGraph: CharactersGraph by inject()
//    private val episodesGraph: EpisodesGraph by inject()
    private val charactersNavigation: CharactersNavigation by inject()

    private val routerModule = module {
        single<Router> { state.value.router }
    }

    init {
        loadKoinModules(routerModule)

//        _state.update {
//            it.copy(
//                graphs = listOf(charactersGraph, episodesGraph)
//            )
//        }

        viewModelScope.launch {
            delay(300)

            // Prevent duplicate navigation when state is restored with a non-splash stack.
            val hasOnlySplash = state.value.backStack.size == 1 &&
                state.value.backStack.firstOrNull() == Splash

            if (hasOnlySplash) {
                charactersNavigation.showCharacters()
                state.value.backStack.remove(Splash)
            }
        }
    }

    override fun onCleared() {
        unloadKoinModules(routerModule)
        super.onCleared()
    }
}
