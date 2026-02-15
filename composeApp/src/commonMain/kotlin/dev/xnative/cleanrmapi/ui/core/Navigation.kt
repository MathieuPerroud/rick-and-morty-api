package dev.xnative.cleanrmapi.ui.core

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface CharacterDestination : NavKey {
    @Serializable
    data object Characters : CharacterDestination

    @Serializable
    data class CharacterDetails(val characterId: Int) : CharacterDestination
}


@Serializable
sealed interface EpisodeDestination : NavKey {
    @Serializable
    data class EpisodeDetails(val episodeId: Int) : EpisodeDestination
}