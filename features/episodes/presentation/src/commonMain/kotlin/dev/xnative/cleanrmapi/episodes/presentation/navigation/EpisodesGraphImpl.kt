package dev.xnative.cleanrmapi.episodes.presentation.navigation

import dev.xnative.cleanrmapi.episodes.navigation.EpisodesGraph
import dev.xnative.cleanrmapi.episodes.presentation.screens.EpisodeDetails
import dev.xnative.cleanrmapi.navigation.Destination
import kotlinx.serialization.modules.PolymorphicModuleBuilder

class EpisodesGraphImpl : EpisodesGraph {

    context(polymorphicModuleBuilder: PolymorphicModuleBuilder<Destination>)
    override fun subclasses() = with(polymorphicModuleBuilder) {
        subclass(
            EpisodeDetails::class,
            EpisodeDetails.serializer()
        )
    }

}

