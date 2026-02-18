package dev.xnative.cleanrmapi.episodes.presentation.navigation

import dev.xnative.cleanrmapi.episodes.navigation.EpisodesNavigation
import dev.xnative.cleanrmapi.episodes.presentation.screens.EpisodeDetails
import dev.xnative.cleanrmapi.navigation.Router

class EpisodesNavigationImpl(
    private val router: Router
) : EpisodesNavigation {

    override fun navigateToEpisodeDetails(episodeId: Int) {
        router.navigateTo(EpisodeDetails(episodeId))
    }

}
