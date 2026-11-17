package org.mathieu.cleanrmapi.ui.navigation.impl

import org.koin.core.module.Module
import org.koin.dsl.module
import org.mathieu.cleanrmapi.presentation.navigation.CharacterDetailsNavigation
import org.mathieu.cleanrmapi.presentation.navigation.CharactersNavigation
import org.mathieu.cleanrmapi.ui.navigation.AppRouter
import org.mathieu.cleanrmapi.ui.screens.characterdetails.CharacterDetailsScreen

/**
 * The single DI wiring point of the scope: binds every feature navigation intention to its
 * implementation over the scope-internal [AppRouter].
 *
 * Loaded once by [org.mathieu.cleanrmapi.ui.navigation.AppScopeViewModel] and unloaded with it,
 * so a binding never outlives the back stack it mutates.
 */
internal fun navigationModule(router: AppRouter): Module = module {

    factory<CharactersNavigation> { CharactersNavigationImpl(router) }

    factory<CharacterDetailsNavigation> { CharacterDetailsNavigationImpl(router) }
}

/** Decides how the scope back stack changes for each characters navigation intention. */
internal class CharactersNavigationImpl(
    private val router: AppRouter
) : CharactersNavigation {

    override fun showCharacterDetails(characterId: Int) {
        router.push(CharacterDetailsScreen(characterId))
    }
}

/** Decides how the scope back stack changes for each details navigation intention. */
internal class CharacterDetailsNavigationImpl(
    private val router: AppRouter
) : CharacterDetailsNavigation {

    override fun goBack() {
        router.pop()
    }
}
