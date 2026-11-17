package org.mathieu.cleanrmapi.presentation.navigation

/**
 * Navigation intentions of the character-details feature.
 *
 * Closing the details screen is a behaviour of this module, so `goBack()` lives here; the scope
 * implements it in `ui.navigation.impl` over the guarded router pop. View models receive it as
 * an injected member delegate and never see the router type.
 */
interface CharacterDetailsNavigation {

    /** Closes the details screen; a no-op while only the root screen is on the stack. */
    fun goBack()
}
