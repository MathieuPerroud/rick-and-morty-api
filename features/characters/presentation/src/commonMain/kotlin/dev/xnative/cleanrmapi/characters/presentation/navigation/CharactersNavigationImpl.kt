package dev.xnative.cleanrmapi.characters.presentation.navigation

import dev.xnative.cleanrmapi.characters.navigation.CharactersNavigation
import dev.xnative.cleanrmapi.navigation.Router
import dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.CharacterDetailsScreen
import dev.xnative.cleanrmapi.characters.presentation.screens.CharactersEntryScreen

class CharactersNavigationImpl(
    private val router: Router
) : CharactersNavigation {

    override fun showCharacters() {
        router.navigateTo(CharactersEntryScreen)
    }

    override fun showCharacterDetails(characterId: Int) {
        router.navigateTo(CharacterDetailsScreen(characterId))
    }

}
