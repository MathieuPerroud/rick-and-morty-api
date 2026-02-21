@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package dev.xnative.cleanrmapi.presentation.localproviders

import androidx.compose.runtime.compositionLocalOf
import dev.xnative.cleanrmapi.navigation.Router

val LocalRouterProvider = compositionLocalOf<Router> {
    throw Exception("No composition local has been provided for router")
}