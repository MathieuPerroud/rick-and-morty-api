package org.mathieu.cleanrmapi.ui.screens.characters

import android.app.Application
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.models.character.Character
import org.mathieu.cleanrmapi.domain.repositories.CharacterRepository
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.ViewModel

sealed interface CharactersAction {
    object ReachedTheBottomOfTheList : CharactersAction

    data class SelectedCharacter(val character: Character): CharactersAction
}

class CharactersViewModel(application: Application) : ViewModel<CharactersState>(CharactersState(), application) {

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

    fun handleAction(action: CharactersAction) {
        when(action) {
            is CharactersAction.SelectedCharacter -> selectedCharacter(action.character)
            CharactersAction.ReachedTheBottomOfTheList -> loadMoreCharacters()
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


data class CharactersState(
    val isLoading: Boolean = true,
    val characters: List<Character> = emptyList(),
    val error: String? = null
)