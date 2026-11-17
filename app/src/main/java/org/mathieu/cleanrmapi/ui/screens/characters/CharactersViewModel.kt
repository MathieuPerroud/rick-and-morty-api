package org.mathieu.cleanrmapi.ui.screens.characters

import android.app.Application
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.models.character.Character
import org.mathieu.cleanrmapi.domain.repositories.CharacterRepository
import org.mathieu.cleanrmapi.presentation.navigation.CharactersNavigation
import org.mathieu.cleanrmapi.ui.core.ViewModel


sealed interface UiEvent

class CharactersViewModel(application: Application) : ViewModel<CharactersState, UiEvent>(CharactersState(), application) {

    private val characterRepository: CharacterRepository by inject()

    private val charactersNavigation: CharactersNavigation by inject()


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

    /** Turns a list selection into the characters navigation intention (UiAction -> VM -> intention). */
    fun onCharacterSelected(character: Character) {
        charactersNavigation.showCharacterDetails(characterId = character.id)
    }

}


data class CharactersState(
    val isLoading: Boolean = true,
    val characters: List<Character> = emptyList(),
    val error: String? = null
)
