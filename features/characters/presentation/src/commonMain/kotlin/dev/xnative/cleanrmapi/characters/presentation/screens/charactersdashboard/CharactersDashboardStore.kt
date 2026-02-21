package dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard

import dev.xnative.cleanrmapi.characters.domain.CharacterRepository
import dev.xnative.cleanrmapi.characters.presentation.components.CharacterDetailsComponent
import dev.xnative.cleanrmapi.characters.presentation.components.ListOfCharactersComponent
import dev.xnative.cleanrmapi.presentation.Store
import dev.xnative.cleanrmapi.presentation.StoreAction
import org.koin.core.component.inject

sealed interface CharactersDashboardContracts {

    data class UiState(
        val listOfCharactersState: ListOfCharactersComponent.UiState =
            ListOfCharactersComponent.Loading,
        val detailSection: DetailSection = NoCharacterSelected
    )

    sealed interface DetailSection

    data object NoCharacterSelected : DetailSection

    data class CharacterSelected(
        val characterDetailsState: CharacterDetailsComponent.UiState =
            CharacterDetailsComponent.Loading
    ) : DetailSection

    interface UiAction : StoreAction<UiState, CharactersDashboardStore>
}

class CharactersDashboardStore: Store<CharactersDashboardContracts.UiState>(
    initialState = CharactersDashboardContracts.UiState()
) {
    init {
        InitializeCharacters.execute(from = this)
    }
}

private data object InitializeCharacters :
    StoreAction<CharactersDashboardContracts.UiState, CharactersDashboardStore> {

    private val characterRepository: CharacterRepository by inject()

    override fun CharactersDashboardStore.reduce() {
        collectData(
            source = characterRepository::getCharacters
        ) {
            onSuccess { characters ->
                updateState {
                    copy(
                        listOfCharactersState = ListOfCharactersComponent.Loaded(
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
