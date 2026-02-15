package dev.xnative.cleanrmapi.ui.core.extensions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Female
import androidx.compose.material.icons.rounded.Help
import androidx.compose.material.icons.rounded.Male
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import cleanrmapiudf.composeapp.generated.resources.Res
import cleanrmapiudf.composeapp.generated.resources.alive
import cleanrmapiudf.composeapp.generated.resources.dead
import cleanrmapiudf.composeapp.generated.resources.female
import cleanrmapiudf.composeapp.generated.resources.genderless
import cleanrmapiudf.composeapp.generated.resources.male
import cleanrmapiudf.composeapp.generated.resources.unknown
import org.jetbrains.compose.resources.stringResource
import dev.xnative.cleanrmapi.domain.character.models.CharacterGender
import dev.xnative.cleanrmapi.domain.character.models.CharacterStatus


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
