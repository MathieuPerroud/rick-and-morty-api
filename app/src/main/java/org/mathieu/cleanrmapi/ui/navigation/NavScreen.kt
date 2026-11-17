package org.mathieu.cleanrmapi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey

/**
 * A back-stack key that is also the screen it represents.
 *
 * Every destination of the application is a [NavScreen]: the key stored in the scope back stack
 * (owned by [AppScopeViewModel]) is the very class that renders itself through [MainComponent],
 * so no route string, marker destination or `when` over keys is ever needed to display it.
 *
 * Keys are deliberately **not** `@Serializable`: the stack lives only as in-memory state inside
 * the scope view model and is never persisted.
 */
interface NavScreen : NavKey {

    /**
     * Renders this screen. The Navigation3 host ([AppNavHost]) calls it for the key currently
     * held by the back stack.
     */
    @Composable
    fun MainComponent(modifier: Modifier = Modifier)
}
