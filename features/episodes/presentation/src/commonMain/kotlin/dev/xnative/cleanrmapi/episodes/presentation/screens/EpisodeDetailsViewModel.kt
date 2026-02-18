package dev.xnative.cleanrmapi.episodes.presentation.screens

import dev.xnative.cleanrmapi.characters.navigation.CharactersNavigation
import dev.xnative.cleanrmapi.domain.character.models.Character
import dev.xnative.cleanrmapi.domain.episode.usecases.GetEpisodeWithCharacters
import dev.xnative.cleanrmapi.presentation.ViewModel
import org.koin.core.component.inject

sealed interface EpisodeDetailsAction {
    data class SelectedCharacter(val character: Character): EpisodeDetailsAction
}

class EpisodeDetailsViewModel(episodeId: Int) :
    ViewModel<EpisodeDetailsState>(EpisodeDetailsState.Loading) {

    private val charactersNavigator: CharactersNavigation by inject()

    init {

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
                    EpisodeDetailsState.Error(message = it.message ?: it.toString())
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
        charactersNavigator.showCharacterDetails(character.id)

}

sealed interface EpisodeDetailsState {
    data object Loading : EpisodeDetailsState

    data class Error(val message: String) : EpisodeDetailsState

    data class Loaded(
        val name: String,
        val airDate: String,
        val episode: String,
        val characters: List<Character>
    ) : EpisodeDetailsState

}