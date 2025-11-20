package org.mathieu.cleanrmapi.ui.screens.characterdetails

import android.app.Application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.repositories.CharacterRepository
import org.mathieu.cleanrmapi.ui.core.ViewModel


sealed interface UiEvent {

    object FailedToLoadCharacterDetails: UiEvent

}

class CharacterDetailsViewModel(application: Application) : ViewModel<CharacterDetailsState, UiEvent>(CharacterDetailsState(), application) {

    private val characterRepository: CharacterRepository by inject()

    fun init(characterId: Int) {

        fetchData(
            source = { characterRepository.getCharacter(id = characterId) }
        ) {

            onSuccess {
                updateState { copy(avatarUrl = it.avatarUrl, name = it.name, error = null) }
            }

            onFailure {
                updateState { copy(error = it.toString()) }
                sendEvent(UiEvent.FailedToLoadCharacterDetails)
            }

            updateState { copy(isLoading = false) }
        }
    }


}


data class CharacterDetailsState(
    val isLoading: Boolean = true,
    val avatarUrl: String = "",
    val name: String = "",
    val error: String? = null
)