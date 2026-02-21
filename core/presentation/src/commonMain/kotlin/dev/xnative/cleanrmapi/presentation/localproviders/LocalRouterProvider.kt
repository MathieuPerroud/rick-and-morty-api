@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package dev.xnative.cleanrmapi.presentation.localproviders

import androidx.compose.runtime.compositionLocalOf
import dev.xnative.cleanrmapi.navigation.Router

/**
 * CompositionLocal exposing the active [Router] to UI layers.
 *
 * It must be provided at app root. Throwing early here makes wiring mistakes explicit.
 */
val LocalRouterProvider = compositionLocalOf<Router> {
    throw Exception("No composition local has been provided for router")
}
