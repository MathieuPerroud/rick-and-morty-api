package dev.xnative.cleanrmapi.characters.presentation.navigation

import dev.xnative.cleanrmapi.characters.navigation.CharactersNavigation
import dev.xnative.cleanrmapi.navigation.Router
import dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.CharacterDetails
import dev.xnative.cleanrmapi.characters.presentation.screens.characters.CharactersList

class CharactersNavigationImpl(
    private val router: Router
) : CharactersNavigation {

    override fun showCharacters() {
        router.navigateTo(CharactersList)
    }

    override fun showCharacterDetails(characterId: Int) {
        router.navigateTo(CharacterDetails(characterId))
    }

}
