package dev.xnative.cleanrmapi.episodes.presentation.navigation

import dev.xnative.cleanrmapi.episodes.navigation.EpisodesNavigation
import dev.xnative.cleanrmapi.episodes.presentation.screens.EpisodeDetailsScreen
import dev.xnative.cleanrmapi.navigation.Router

/**
 * Default Episodes navigation contract implementation.
 */
class EpisodesNavigationImpl(
    private val router: Router
) : EpisodesNavigation {

    override fun navigateToEpisodeDetails(episodeId: Int) {
        router.navigateTo(EpisodeDetailsScreen(episodeId))
    }

}
