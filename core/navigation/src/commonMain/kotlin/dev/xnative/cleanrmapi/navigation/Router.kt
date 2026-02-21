package dev.xnative.cleanrmapi.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.Flow

/**
 * App-level navigation contract shared across features.
 *
 * Implementations are responsible for mutating the active back stack and exposing
 * reactive information about which screen is currently on top.
 */
interface Router {

    /**
     * Pushes one or several [NavKey] instances to the active back stack.
     */
    fun navigateTo(vararg screens: NavKey)

    /**
     * Pops the current screen when possible.
     */
    fun navigateBack()

    /**
     * Emits `true` when [screen] is currently displayed on top of the back stack.
     */
    fun getActiveStatusForScreen(screen: NavKey): Flow<Boolean>

}
