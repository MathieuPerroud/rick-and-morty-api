package dev.xnative.cleanrmapi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey

abstract class Destination : NavKey {
    @Composable
    abstract fun Screen(
        router: Router
    )
}