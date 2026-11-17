package org.mathieu.cleanrmapi.presentation.navigation

/**
 * Navigation intentions of the characters feature.
 *
 * Declared under `presentation/navigation` and implemented by the scope in
 * `ui.navigation.impl` over its internal router. View models receive it as an injected member
 * delegate, so neither a composable nor a contract signature ever sees the router type.
 */
interface CharactersNavigation {

    /** Shows the details of one character on top of the characters list. */
    fun showCharacterDetails(characterId: Int)
}
