package dev.xnative.cleanrmapi.characters.presentation.screens.characterslist

import dev.xnative.cleanrmapi.characters.domain.CharacterRepository
import dev.xnative.cleanrmapi.characters.presentation.components.ListOfCharactersComponent
import dev.xnative.cleanrmapi.characters.presentation.components.ListOfCharactersComponent.Loaded
import dev.xnative.cleanrmapi.characters.presentation.components.ListOfCharactersComponent.Loading
import dev.xnative.cleanrmapi.presentation.Store
import dev.xnative.cleanrmapi.presentation.StoreAction
import org.koin.core.component.inject

/**
 * Contracts owned by `CharactersList` screen.
 */
sealed interface CharactersListContracts {

    /**
     * Aggregates component states rendered by the screen.
     */
    data class UiState(
        val listOfCharactersState: ListOfCharactersComponent.UiState = Loading
    )

    /**
     * Marker interface for all actions reducible by [CharactersListStore].
     */
    interface UiAction : StoreAction<UiState, CharactersListStore>
}

/**
 * Store responsible for list state and first load bootstrap.
 */
class CharactersListStore : Store<CharactersListContracts.UiState>(
    initialState = CharactersListContracts.UiState()
) {
    init {
        Initialize.execute(from = this)
    }
}

/**
 * Initial data load triggered when the store is created.
 */
private data object Initialize :
    StoreAction<CharactersListContracts.UiState, CharactersListStore> {

    private val characterRepository: CharacterRepository by inject()

    override fun CharactersListStore.reduce() {
        collectData(
            source = characterRepository::getCharacters
        ) {
            onSuccess { characters ->
                updateState {
                    copy(
                        listOfCharactersState = Loaded(
                            characters = characters
                        )
                    )
                }
            }

            onFailure { throwable ->
                updateState {
                    copy(
                        listOfCharactersState = ListOfCharactersComponent.Error(
                            message = throwable.toString()
                        )
                    )
                }
            }
        }
    }
}
