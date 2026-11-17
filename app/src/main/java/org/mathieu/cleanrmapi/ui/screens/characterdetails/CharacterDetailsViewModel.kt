package org.mathieu.cleanrmapi.ui.screens.characterdetails

import android.app.Application
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.repositories.CharacterRepository
import org.mathieu.cleanrmapi.presentation.navigation.CharacterDetailsNavigation
import org.mathieu.cleanrmapi.ui.core.ViewModel


/**
 * Event channel of the details screen.
 *
 * No navigation-only event exists any more: closing the screen after a failed load is folded
 * into the view model, which calls the navigation intention directly.
 */
sealed interface UiEvent

class CharacterDetailsViewModel(application: Application) : ViewModel<CharacterDetailsState, UiEvent>(CharacterDetailsState(), application) {

    private val characterRepository: CharacterRepository by inject()

    private val characterDetailsNavigation: CharacterDetailsNavigation by inject()

    fun init(characterId: Int) {

        fetchData(
            source = { characterRepository.getCharacter(id = characterId) }
        ) {

            onSuccess {
                updateState { copy(avatarUrl = it.avatarUrl, name = it.name, error = null) }
            }

            onFailure {
                updateState { copy(error = it.toString()) }
                characterDetailsNavigation.goBack()
            }

            updateState { copy(isLoading = false) }
        }
    }

    /** Back button pressed: closes this screen through the navigation intention. */
    fun onBackClicked() {
        characterDetailsNavigation.goBack()
    }


}


data class CharacterDetailsState(
    val isLoading: Boolean = true,
    val avatarUrl: String = "",
    val name: String = "",
    val error: String? = null
)
