package org.mathieu.cleanrmapi.ui.screens.characters

import android.app.Application
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.character.CharacterRepository
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.ViewModel
import org.mathieu.cleanrmapi.ui.screens.characters.CharactersContracts.*

class CharactersViewModel(application: Application) : ViewModel<UiState>(UiState(), application) {

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
        sendEvent(Destination.CharacterDetails(character.id.toString()))


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
    object ReachedTheBottomOfTheList : UiAction
    data class SelectedCharacter(val character: Character): UiAction

}


