package dev.xnative.cleanrmapi.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.Flow

interface Router {

    fun navigateTo(vararg screens: NavKey)

    fun navigateBack()

    fun getActiveStatusForScreen(screen: NavKey): Flow<Boolean>

}