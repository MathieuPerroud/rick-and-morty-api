package dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails

import dev.xnative.cleanrmapi.characters.domain.CharacterRepository
import dev.xnative.cleanrmapi.characters.presentation.components.CharacterDetailsComponent
import dev.xnative.cleanrmapi.characters.presentation.extensions.toLoadedCharacterState
import dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.CharacterDetailsContracts.UiState
import dev.xnative.cleanrmapi.presentation.Store
import dev.xnative.cleanrmapi.presentation.StoreAction
import org.koin.core.component.inject

/**
 * Contracts owned by the character details screen.
 */
sealed interface CharacterDetailsContracts {

    /**
     * Aggregates all component states rendered by details screen.
     */
    data class UiState(
        val characterDetailsState: CharacterDetailsComponent.UiState =
            CharacterDetailsComponent.Loading
    )

    /**
     * Marker interface for details screen actions.
     */
    interface UiAction : StoreAction<UiState, CharacterDetailsStore>
}

/**
 * Store responsible for loading and exposing a single character details state.
 */
class CharacterDetailsStore(characterId: Int) : Store<UiState>(
    initialState = UiState()
) {
    init {
        Initialize(characterId).execute(from = this)
    }
}

/**
 * Bootstrap action loading character details with a local-first strategy.
 */
private class Initialize(private val characterId: Int) : StoreAction<UiState, CharacterDetailsStore> {

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
