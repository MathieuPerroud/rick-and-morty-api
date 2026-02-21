package dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails

import dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.CharacterDetailsContracts.UiAction
import dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.CharacterDetailsContracts.UiState
import dev.xnative.cleanrmapi.presentation.StoreViewModel

class CharacterDetailsViewModel(characterId: Int) :
    StoreViewModel<UiState, CharacterDetailsStore, UiAction>(
        store = CharacterDetailsStore(
            characterId = characterId
        )
    )