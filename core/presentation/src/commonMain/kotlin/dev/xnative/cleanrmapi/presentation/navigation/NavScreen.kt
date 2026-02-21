package dev.xnative.cleanrmapi.presentation.navigation

import androidx.navigation3.runtime.NavKey
import dev.xnative.cleanrmapi.presentation.UiComponent

/**
 * A serializable navigation destination that can render itself.
 *
 * `NavScreen` combines:
 * - [NavKey] for Navigation3 back stack serialization.
 * - [UiComponent] for the screen's main composable entry point.
 */
interface NavScreen : NavKey, UiComponent
