package dev.xnative.cleanrmapi.ui.screens.characters

import org.koin.core.component.inject
import dev.xnative.cleanrmapi.domain.character.CharacterRepository
import dev.xnative.cleanrmapi.domain.character.models.Character
import dev.xnative.cleanrmapi.ui.core.CharacterDestination
import dev.xnative.cleanrmapi.ui.core.ViewModel
import dev.xnative.cleanrmapi.ui.screens.characters.CharactersContracts.ReachedTheBottomOfTheList
import dev.xnative.cleanrmapi.ui.screens.characters.CharactersContracts.SelectedCharacter
import dev.xnative.cleanrmapi.ui.screens.characters.CharactersContracts.UiAction
import dev.xnative.cleanrmapi.ui.screens.characters.CharactersContracts.UiState

class CharactersViewModel : ViewModel<UiState>(UiState()) {

    private val characterRepository: CharacterRepository by inject()


    init {

        collectData(
            source = { characterRepository.getCharacters() }
        ) {

            onSuccess {
                updateState { copy(characters = it, error = null) }
            }

            onFailure {
                updateState { copy(characters = emptyList(), error = it.toString()) }
            }

            updateState { copy(isLoading = false) }
        }

    }

    fun handleAction(action: UiAction) {
        when(action) {
            is SelectedCharacter -> selectedCharacter(action.character)
            ReachedTheBottomOfTheList -> loadMoreCharacters()
        }
    }


    private fun selectedCharacter(character: Character) =
        sendEvent(CharacterDestination.CharacterDetails(character.id))


    private fun loadMoreCharacters() =
        fetchData(
            source = characterRepository::loadMore
        ) {

            onFailure {
                updateState { copy(error = it.toString()) }
            }

        }

}

interface CharactersContracts {
    data class UiState(
        val isLoading: Boolean = true,
        val characters: List<Character> = emptyList(),
        val error: String? = null
    )

    sealed interface UiAction
    data object ReachedTheBottomOfTheList : UiAction
    data class SelectedCharacter(val character: Character): UiAction

}


