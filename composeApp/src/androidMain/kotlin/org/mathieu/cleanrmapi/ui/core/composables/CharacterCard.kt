package org.mathieu.cleanrmapi.ui.core.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.ui.core.theme.SurfaceColor


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterCard(
    modifier: Modifier, character: Character
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

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
