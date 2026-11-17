package org.mathieu.cleanrmapi.ui.navigation

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.mathieu.cleanrmapi.ui.navigation.impl.navigationModule
import org.mathieu.cleanrmapi.ui.screens.characters.CharactersScreen

/**
 * Owner of the application back stack.
 *
 * The stack is plain state ([SnapshotStateList]) held only here — it is never pushed from
 * composition and never persisted — and it is seeded with the root screen (the characters list)
 * as its initial value, so it stays non-empty for as long as the application renders.
 *
 * It is also the scope's single navigation wiring point: the feature intentions are bound to
 * this view model's router on init and released with it.
 */
class AppScopeViewModel(application: Application) : AndroidViewModel(application) {

    /** Back-stack keys, root screen first. */
    val stack: SnapshotStateList<NavScreen> = mutableStateListOf(CharactersScreen)

    /** Scope-internal operations over [stack], consumed by `navigation.impl` only. */
    internal val router = AppRouter(stack)

    private val navigationBindings = navigationModule(router)

    init {
        loadKoinModules(navigationBindings)
    }

    override fun onCleared() {
        unloadKoinModules(navigationBindings)
        super.onCleared()
    }
}
