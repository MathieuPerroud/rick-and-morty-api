package dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.actions

import dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.CharacterDetailsContracts
import dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.CharacterDetailsStore
import dev.xnative.cleanrmapi.episodes.navigation.EpisodesNavigation
import org.koin.core.component.inject

/**
 * Navigates from character details to a selected episode details screen.
 */
class NavigateToEpisodeDetails(private val episodeId: Int) : CharacterDetailsContracts.UiAction {
    private val episodeNavigator: EpisodesNavigation by inject()

    override fun CharacterDetailsStore.reduce() {
        episodeNavigator.navigateToEpisodeDetails(episodeId)
    }
}
