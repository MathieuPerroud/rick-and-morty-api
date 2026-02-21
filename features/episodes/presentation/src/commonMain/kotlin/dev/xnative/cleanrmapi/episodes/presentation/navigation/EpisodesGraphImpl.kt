package dev.xnative.cleanrmapi.episodes.presentation.navigation

import androidx.navigation3.runtime.NavKey
import dev.xnative.cleanrmapi.episodes.navigation.EpisodesGraph
import dev.xnative.cleanrmapi.episodes.presentation.screens.EpisodeDetailsScreen
import kotlinx.serialization.modules.PolymorphicModuleBuilder

/**
 * Registers Episodes feature navigation keys for polymorphic serialization.
 */
class EpisodesGraphImpl : EpisodesGraph {

    context(polymorphicModuleBuilder: PolymorphicModuleBuilder<NavKey>)
    override fun subclasses() = with(polymorphicModuleBuilder) {
        subclass(
            EpisodeDetailsScreen::class,
            EpisodeDetailsScreen.serializer()
        )
    }

}
