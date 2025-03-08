package org.mathieu.cleanrmapi.ui.screens.episodedetails

import android.app.Application
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.episode.usecases.GetEpisodeWithCharacters
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.ViewModel

sealed interface EpisodeDetailsAction {
    data class SelectedCharacter(val character: Character): EpisodeDetailsAction
}

class EpisodeDetailsViewModel(application: Application) :
    ViewModel<EpisodeDetailsState>(EpisodeDetailsState.Loading, application) {

    fun init(episodeId: Int) {

        fetchData(
            source = { GetEpisodeWithCharacters(episodeId = episodeId) }
        ) {
            onSuccess { details ->
                updateState {
                    EpisodeDetailsState.Loaded(
                        name = details.name,
                        airDate = details.airDate,
                        episode = details.episode,
                        characters = details.characters
                    )
                }
            }

            onFailure {
                updateState {
                    EpisodeDetailsState.Error(message = it.localizedMessage ?: it.toString())
                }
            }

        }

    }


    fun handleAction(action: EpisodeDetailsAction) {
        when(action) {
            is EpisodeDetailsAction.SelectedCharacter -> selectedCharacter(action.character)
        }
    }


    private fun selectedCharacter(character: Character) =
        sendEvent(Destination.CharacterDetails(character.id.toString()))



}

sealed interface EpisodeDetailsState {
    object Loading : EpisodeDetailsState

    data class Error(val message: String) : EpisodeDetailsState

    data class Loaded(
        val name: String,
        val airDate: String,
        val episode: String,
        val characters: List<Character>
    ) : EpisodeDetailsState

}