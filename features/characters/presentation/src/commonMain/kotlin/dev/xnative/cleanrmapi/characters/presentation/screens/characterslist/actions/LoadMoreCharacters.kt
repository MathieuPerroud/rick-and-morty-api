package dev.xnative.cleanrmapi.characters.presentation.screens.characterslist.actions

import dev.xnative.cleanrmapi.characters.presentation.components.ListOfCharactersComponent
import dev.xnative.cleanrmapi.characters.presentation.components.usecases.LoadMoreCharactersUseCase
import dev.xnative.cleanrmapi.characters.presentation.screens.characterslist.CharactersListContracts
import dev.xnative.cleanrmapi.characters.presentation.screens.characterslist.CharactersListStore

data object LoadMoreCharacters : CharactersListContracts.UiAction {

    private val loadMoreCharactersUseCase = LoadMoreCharactersUseCase()

    override fun CharactersListStore.reduce() {
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
