package dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.actions

import dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.CharacterDetailsContracts
import dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.CharacterDetailsStore
import dev.xnative.cleanrmapi.episodes.navigation.EpisodesNavigation
import org.koin.core.component.inject

class NavigateToEpisodeDetails(private val episodeId: Int): CharacterDetailsContracts.UiAction {
    private val episodeNavigator: EpisodesNavigation by inject()

    override fun CharacterDetailsStore.reduce() {
        episodeNavigator.navigateToEpisodeDetails(episodeId)
    }
}