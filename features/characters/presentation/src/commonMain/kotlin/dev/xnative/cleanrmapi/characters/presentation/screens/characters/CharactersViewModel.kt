package dev.xnative.cleanrmapi.characters.presentation.screens.characters

import dev.xnative.cleanrmapi.characters.domain.CharacterRepository
import dev.xnative.cleanrmapi.domain.character.models.CharacterPreview
import dev.xnative.cleanrmapi.presentation.ViewModel
import dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.CharacterDetails
import dev.xnative.cleanrmapi.characters.presentation.screens.characters.CharactersContracts.ReachedTheBottomOfTheList
import dev.xnative.cleanrmapi.characters.presentation.screens.characters.CharactersContracts.SelectedCharacter
import dev.xnative.cleanrmapi.characters.presentation.screens.characters.CharactersContracts.UiAction
import dev.xnative.cleanrmapi.characters.presentation.screens.characters.CharactersContracts.UiState
import org.koin.core.component.inject

interface CharactersContracts {
    data class UiState(
        val isLoading: Boolean = true,
        val characters: List<CharacterPreview> = emptyList(),
        val error: String? = null
    )

    sealed interface UiAction
    data object ReachedTheBottomOfTheList : UiAction
    data class SelectedCharacter(val character: CharacterPreview): UiAction

}


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


    private fun selectedCharacter(character: CharacterPreview) =
        sendEvent(CharacterDetails(character.id))


    private fun loadMoreCharacters() =
        fetchData(
            source = characterRepository::loadMore
        ) {

            onFailure {
                updateState { copy(error = it.toString()) }
            }

        }

}
