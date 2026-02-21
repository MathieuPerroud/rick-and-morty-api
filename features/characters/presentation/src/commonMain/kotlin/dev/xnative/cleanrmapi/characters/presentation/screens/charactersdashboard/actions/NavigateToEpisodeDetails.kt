package dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.actions

import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.CharactersDashboardContracts
import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.CharactersDashboardStore
import dev.xnative.cleanrmapi.episodes.navigation.EpisodesNavigation
import org.koin.core.component.inject

class NavigateToEpisodeDetails(
    private val episodeId: Int
) : CharactersDashboardContracts.UiAction {

    private val episodesNavigation: EpisodesNavigation by inject()

    override fun CharactersDashboardStore.reduce() {
        episodesNavigation.navigateToEpisodeDetails(episodeId)
    }
}
