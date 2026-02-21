package dev.xnative.cleanrmapi.characters.presentation.extensions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Female
import androidx.compose.material.icons.rounded.Help
import androidx.compose.material.icons.rounded.Male
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import cleanrmapiudf.core.presentation.generated.resources.Res
import cleanrmapiudf.core.presentation.generated.resources.alive
import cleanrmapiudf.core.presentation.generated.resources.dead
import cleanrmapiudf.core.presentation.generated.resources.female
import cleanrmapiudf.core.presentation.generated.resources.genderless
import cleanrmapiudf.core.presentation.generated.resources.male
import cleanrmapiudf.core.presentation.generated.resources.unknown
import dev.xnative.cleanrmapi.characters.domain.models.CharacterDetails
import dev.xnative.cleanrmapi.characters.domain.models.CharacterGender
import dev.xnative.cleanrmapi.characters.domain.models.CharacterStatus
import dev.xnative.cleanrmapi.characters.presentation.components.CharacterDetailsComponent
import org.jetbrains.compose.resources.stringResource

val CharacterStatus.imageVector: ImageVector
    @Composable get() = when (this) {
        CharacterStatus.Alive -> Icons.Rounded.Favorite
        CharacterStatus.Dead -> Icons.Filled.CatchingPokemon
        CharacterStatus.Unknown -> Icons.Rounded.Help
    }

val CharacterStatus.text: String
    @Composable get() = when (this) {
        CharacterStatus.Alive -> stringResource(Res.string.alive)
        CharacterStatus.Dead -> stringResource(Res.string.dead)
        CharacterStatus.Unknown -> stringResource(Res.string.unknown)
    }

val CharacterGender.imageVector: ImageVector
    @Composable get() = when (this) {
        CharacterGender.Female -> Icons.Rounded.Female
        CharacterGender.Male -> Icons.Rounded.Male
        CharacterGender.Genderless -> Icons.Rounded.Remove
        CharacterGender.Unknown -> Icons.Rounded.Help
    }

val CharacterGender.text: String
    @Composable get() = when (this) {
        CharacterGender.Female -> stringResource(Res.string.female)
        CharacterGender.Male -> stringResource(Res.string.male)
        CharacterGender.Genderless -> stringResource(Res.string.genderless)
        CharacterGender.Unknown -> stringResource(Res.string.unknown)
    }

fun CharacterDetails.toLoadedCharacterState() = CharacterDetailsComponent.Loaded(
    name = name,
    avatarUrl = avatarUrl,
    episodes = episodes,
    status = status,
    gender = gender,
    origin = origin,
    location = location
)