package org.mathieu.cleanrmapi.ui.core.extensions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Female
import androidx.compose.material.icons.rounded.Help
import androidx.compose.material.icons.rounded.Male
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import org.mathieu.cleanrmapi.R
import org.mathieu.cleanrmapi.domain.character.models.CharacterGender
import org.mathieu.cleanrmapi.domain.character.models.CharacterStatus


val CharacterStatus.imageVector: ImageVector
    @Composable get() = when (this) {
        CharacterStatus.Alive -> Icons.Rounded.Favorite
        CharacterStatus.Dead -> Icons.Filled.CatchingPokemon
        CharacterStatus.Unknown -> Icons.Rounded.Help
    }

val CharacterStatus.text: String
    @Composable get() = when (this) {
        CharacterStatus.Alive -> stringResource(id = R.string.alive)
        CharacterStatus.Dead -> stringResource(id = R.string.dead)
        CharacterStatus.Unknown -> stringResource(id = R.string.unknown)
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
        CharacterGender.Female -> stringResource(id = R.string.female)
        CharacterGender.Male -> stringResource(id = R.string.male)
        CharacterGender.Genderless -> stringResource(id = R.string.genderless)
        CharacterGender.Unknown -> stringResource(id = R.string.unknown)
    }
