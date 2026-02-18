package dev.xnative.cleanrmapi.app.router

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import dev.xnative.cleanrmapi.navigation.Destination
import dev.xnative.cleanrmapi.navigation.Router

class AppRouter(private val backStack: NavBackStack<NavKey>) : Router {
    override fun <T : Destination> navigateTo(destination: T) {
        backStack.add(destination)
    }

    override fun navigateBack() {
        backStack.removeLastOrNull()
    }
}