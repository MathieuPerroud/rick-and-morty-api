package dev.xnative.cleanrmapi.characters.presentation.screens.characterslist

import dev.xnative.cleanrmapi.characters.domain.CharacterRepository
import dev.xnative.cleanrmapi.characters.presentation.components.ListOfCharactersComponent
import dev.xnative.cleanrmapi.characters.presentation.components.ListOfCharactersComponent.Loaded
import dev.xnative.cleanrmapi.characters.presentation.components.ListOfCharactersComponent.Loading
import dev.xnative.cleanrmapi.presentation.Store
import dev.xnative.cleanrmapi.presentation.StoreAction
import org.koin.core.component.inject

sealed interface CharactersListContracts {

    data class UiState(
        val listOfCharactersState: ListOfCharactersComponent.UiState = Loading
    )

    interface UiAction : StoreAction<UiState, CharactersListStore>
}

class CharactersListStore : Store<CharactersListContracts.UiState>(
    initialState = CharactersListContracts.UiState()
) {
    init {
        Initialize.execute(from = this)
    }
}

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
