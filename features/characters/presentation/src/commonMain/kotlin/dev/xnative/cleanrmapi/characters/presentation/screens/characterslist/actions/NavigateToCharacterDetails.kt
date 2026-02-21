package dev.xnative.cleanrmapi.characters.presentation.screens.characterslist.actions

import dev.xnative.cleanrmapi.characters.navigation.CharactersNavigation
import dev.xnative.cleanrmapi.characters.presentation.screens.characterslist.CharactersListContracts
import dev.xnative.cleanrmapi.characters.presentation.screens.characterslist.CharactersListStore
import dev.xnative.cleanrmapi.domain.character.models.CharacterPreview
import org.koin.core.component.inject

class NavigateToCharacterDetails(
    val character: CharacterPreview
) : CharactersListContracts.UiAction {

    private val charactersNavigation: CharactersNavigation by inject()

    override fun CharactersListStore.reduce() {
        charactersNavigation.showCharacterDetails(characterId = character.id)
    }
}
