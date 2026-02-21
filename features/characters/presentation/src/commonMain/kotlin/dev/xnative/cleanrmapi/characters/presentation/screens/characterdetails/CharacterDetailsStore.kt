package dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails

import dev.xnative.cleanrmapi.characters.domain.CharacterRepository
import dev.xnative.cleanrmapi.characters.presentation.components.CharacterDetailsComponent
import dev.xnative.cleanrmapi.characters.presentation.extensions.toLoadedCharacterState
import dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.CharacterDetailsContracts.UiState
import dev.xnative.cleanrmapi.presentation.Store
import dev.xnative.cleanrmapi.presentation.StoreAction
import org.koin.core.component.inject

sealed interface CharacterDetailsContracts {

    data class UiState(
        val characterDetailsState: CharacterDetailsComponent.UiState =
            CharacterDetailsComponent.Loading
    )
    interface UiAction : StoreAction<UiState, CharacterDetailsStore>
}

class CharacterDetailsStore(characterId: Int) : Store<UiState>(
    initialState = UiState()
) {
    init {
        Initialize(characterId).execute(from = this)
    }
}

private class Initialize(private val characterId: Int): StoreAction<UiState, CharacterDetailsStore>  {

    private val characterRepository: CharacterRepository by inject()

    override fun CharacterDetailsStore.reduce() {
        fetchData(
            source = { characterRepository.getCharacterDetailedLocalFirst(id = characterId) }
        ) {
            onSuccess { details ->
                updateState {
                    UiState(
                        characterDetailsState = details.toLoadedCharacterState()
                    )
                }
            }
        }

    }

}