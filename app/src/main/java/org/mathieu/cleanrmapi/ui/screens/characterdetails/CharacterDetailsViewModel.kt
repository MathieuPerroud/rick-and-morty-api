package org.mathieu.cleanrmapi.ui.screens.characterdetails

import android.app.Application
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.repositories.CharacterRepository
import org.mathieu.cleanrmapi.ui.core.ViewModel


class CharacterDetailsViewModel(application: Application) : ViewModel<CharacterDetailsState>(CharacterDetailsState(), application) {

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