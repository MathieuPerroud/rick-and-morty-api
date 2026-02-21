package dev.xnative.cleanrmapi.app.router

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import dev.xnative.cleanrmapi.navigation.Router
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class AppRouter(private val backStack: NavBackStack<NavKey>) : Router {

    private val screenOnTop = MutableStateFlow(backStack.last())

    override fun navigateTo(vararg screens: NavKey) {
        backStack.addAll(screens)
        screenOnTop.tryEmit(backStack.last())
    }

    override fun navigateBack() {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
            screenOnTop.tryEmit(backStack.last())
        }
    }

    override fun getActiveStatusForScreen(screen: NavKey): Flow<Boolean> =
        screenOnTop.map { it == screen }

}
