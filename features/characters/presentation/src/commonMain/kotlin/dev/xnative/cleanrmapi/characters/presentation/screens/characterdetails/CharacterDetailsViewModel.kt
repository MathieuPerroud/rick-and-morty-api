package dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails

import org.koin.core.component.inject
import dev.xnative.cleanrmapi.characters.domain.CharacterRepository
import dev.xnative.cleanrmapi.characters.domain.models.CharacterGender
import dev.xnative.cleanrmapi.characters.domain.models.CharacterStatus
import dev.xnative.cleanrmapi.domain.episode.models.EpisodePreview
import dev.xnative.cleanrmapi.presentation.ViewModel
import dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.CharacterDetailsContracts.*
import dev.xnative.cleanrmapi.episodes.navigation.EpisodesNavigation

sealed interface CharacterDetailsContracts {

    sealed interface UiState {
        object Loading : UiState

        data class Error(val message: String) : UiState

        data class Loaded(
            val name: String,
            val avatarUrl: String,
            val episodes: List<EpisodePreview>,
            val status: CharacterStatus,
            val gender: CharacterGender,
            val origin: String,
            val location: String,
        ) : UiState

    }

    sealed interface UiAction
    data class SelectedEpisode(val episode: EpisodePreview): UiAction
}

class CharacterDetailsViewModel(characterId: Int) :
    ViewModel<UiState>(UiState.Loading) {

    private val characterRepository: CharacterRepository by inject()
    private val episodeNavigator: EpisodesNavigation by inject()

    init {

        fetchData(
            source = { characterRepository.getCharacterDetailed(id = characterId) }
        ) {

            onSuccess { details ->
                updateState {
                    UiState.Loaded(
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
                    UiState.Error(message = it.message ?: it.toString())
                }
            }


        }


    }

    fun handleAction(action: UiAction) {
        when(action) {
            is SelectedEpisode ->
                episodeNavigator.navigateToEpisodeDetails(action.episode.id)
        }
    }

}
