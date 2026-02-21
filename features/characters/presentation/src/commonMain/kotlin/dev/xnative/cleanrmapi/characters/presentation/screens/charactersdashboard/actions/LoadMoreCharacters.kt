package dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.actions

import dev.xnative.cleanrmapi.characters.presentation.components.ListOfCharactersComponent
import dev.xnative.cleanrmapi.characters.presentation.components.usecases.LoadMoreCharactersUseCase
import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.CharactersDashboardContracts
import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.CharactersDashboardStore

/**
 * Triggers pagination from the dashboard list pane.
 */
data object LoadMoreCharacters : CharactersDashboardContracts.UiAction {

    private val loadMoreCharactersUseCase = LoadMoreCharactersUseCase()

    override fun CharactersDashboardStore.reduce() {
        loadMoreCharactersUseCase.execute(
            getLoadedState = {
                state.value.listOfCharactersState as? ListOfCharactersComponent.Loaded
            },
            setLoadedState = { loadedState ->
                updateState {
                    copy(
                        listOfCharactersState = loadedState
                    )
                }
            }
        ) { source, onResult ->
            fetchData(
                source = source,
                onResult = onResult
            )
        }
    }
}
