package org.mathieu.cleanrmapi.ui.screens.characterdetails

import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.character.CharacterRepository
import org.mathieu.cleanrmapi.domain.character.models.CharacterGender
import org.mathieu.cleanrmapi.domain.character.models.CharacterStatus
import org.mathieu.cleanrmapi.domain.episode.models.Episode
import org.mathieu.cleanrmapi.domain.location.LocationPreview
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.ViewModel

sealed interface CharacterDetailsAction {
    data class SelectedEpisode(val episode: Episode): CharacterDetailsAction
}

class CharacterDetailsViewModel :
    ViewModel<CharacterDetailsState>(CharacterDetailsState.Loading) {

    private val characterRepository: CharacterRepository by inject()

    fun init(characterId: Int) {

        fetchData(
            source = { characterRepository.getCharacterDetailed(id = characterId) }
        ) {

            onSuccess { details ->
                updateState {
                    CharacterDetailsState.Loaded(
                        name = details.name,
                        avatarUrl = details.avatarUrl,
                        episodes = details.episodes,
                        status = details.status,
                        gender = details.gender,
                        origin = details.origin,
                        location = details.location
                    )
                }
            }

            onFailure {
                updateState {
                    CharacterDetailsState.Error(message = it.message ?: it.toString())
                }
            }


        }


    }

    fun handleAction(action: CharacterDetailsAction) {
        when(action) {
            is CharacterDetailsAction.SelectedEpisode ->
                sendEvent(Destination.EpisodeDetails(action.episode.id.toString()))
        }
    }


}

sealed interface CharacterDetailsState {
    object Loading : CharacterDetailsState

    data class Error(val message: String) : CharacterDetailsState

    data class Loaded(
        val name: String,
        val avatarUrl: String,
        val episodes: List<Episode>,
        val status: CharacterStatus,
        val gender: CharacterGender,
        val origin: LocationPreview,
        val location: LocationPreview,
    ) : CharacterDetailsState

}