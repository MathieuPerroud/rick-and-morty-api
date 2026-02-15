package dev.xnative.cleanrmapi.ui.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.xnative.cleanrmapi.domain.character.models.Character
import dev.xnative.cleanrmapi.ui.core.theme.SurfaceColor

@Composable
fun CharacterCard(
    modifier: Modifier, character: Character
) = BoxWithConstraints {

    val screenWidth = this.maxWidth

    Box(
        modifier = modifier
            .height(screenWidth / 2)
            .fillMaxWidth()
    ) {

        Avatar(url = character.avatarUrl)

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(SurfaceColor.copy(alpha = 0.7f))
                .basicMarquee(iterations = Int.MAX_VALUE)
                .padding(8.dp),
            text = character.name,
            textAlign = TextAlign.Center
        )

    }

}
