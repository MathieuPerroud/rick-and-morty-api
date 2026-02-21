package dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard

import dev.xnative.cleanrmapi.characters.domain.CharacterRepository
import dev.xnative.cleanrmapi.characters.presentation.components.CharacterDetailsComponent
import dev.xnative.cleanrmapi.characters.presentation.components.ListOfCharactersComponent
import dev.xnative.cleanrmapi.presentation.Store
import dev.xnative.cleanrmapi.presentation.StoreAction
import org.koin.core.component.inject

/**
 * Contracts owned by the dashboard screen.
 */
sealed interface CharactersDashboardContracts {

    /**
     * Dashboard combines a list section and a detail section in one state tree.
     */
    data class UiState(
        val listOfCharactersState: ListOfCharactersComponent.UiState =
            ListOfCharactersComponent.Loading,
        val detailSection: DetailSection = NoCharacterSelected
    )

    sealed interface DetailSection

    /**
     * No character selected yet: show placeholder in details pane.
     */
    data object NoCharacterSelected : DetailSection

    /**
     * Character selected: details pane renders [characterDetailsState].
     */
    data class CharacterSelected(
        val characterDetailsState: CharacterDetailsComponent.UiState =
            CharacterDetailsComponent.Loading
    ) : DetailSection

    /**
     * Marker interface for all dashboard actions.
     */
    interface UiAction : StoreAction<UiState, CharactersDashboardStore>
}

/**
 * Store responsible for horizontal dashboard state orchestration.
 */
class CharactersDashboardStore : Store<CharactersDashboardContracts.UiState>(
    initialState = CharactersDashboardContracts.UiState()
) {
    init {
        InitializeCharacters.execute(from = this)
    }
}

/**
 * Initial characters load when the dashboard store is created.
 */
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
