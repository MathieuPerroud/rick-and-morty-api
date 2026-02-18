package dev.xnative.cleanrmapi.characters.data.extensions

import dev.xnative.cleanrmapi.common.tryOrNull
import dev.xnative.cleanrmapi.data.local.objects.CharacterObject
import dev.xnative.cleanrmapi.characters.domain.models.CharacterDetails
import dev.xnative.cleanrmapi.characters.domain.models.CharacterGender
import dev.xnative.cleanrmapi.characters.domain.models.CharacterStatus
import dev.xnative.cleanrmapi.domain.episode.models.EpisodePreview

internal suspend fun CharacterObject.toDetailedModel(
    idsToEpisodesConverter: suspend (episodesIds: String) -> List<EpisodePreview> = { emptyList() }
) = CharacterDetails(
    id = id,
    name = name,
    status = tryOrNull { CharacterStatus.valueOf(status) } ?: CharacterStatus.Unknown,
    species = species,
    type = type,
    gender = tryOrNull { CharacterGender.valueOf(gender) } ?: CharacterGender.Unknown,
    origin = originName,
    location = locationName,
    avatarUrl = image,
    episodes = idsToEpisodesConverter(episodesIds)
)
