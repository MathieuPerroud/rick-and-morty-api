package org.mathieu.cleanrmapi.ui.screens.characterdetails

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.mathieu.cleanrmapi.domain.character.models.CharacterGender
import org.mathieu.cleanrmapi.domain.character.models.CharacterStatus
import org.mathieu.cleanrmapi.domain.episode.models.Episode
import org.mathieu.cleanrmapi.ui.core.composables.Avatar
import org.mathieu.cleanrmapi.ui.core.composables.BackArrow
import org.mathieu.cleanrmapi.ui.core.composables.IconWithImage
import org.mathieu.cleanrmapi.ui.core.composables.PreviewContent
import org.mathieu.cleanrmapi.ui.core.composables.Screen
import org.mathieu.cleanrmapi.ui.core.extensions.imageVector
import org.mathieu.cleanrmapi.ui.core.extensions.text
import org.mathieu.cleanrmapi.ui.core.theme.PrimaryColor
import org.mathieu.cleanrmapi.ui.core.theme.SurfaceColor

@Composable
fun CharacterDetailsScreen(
    navController: NavController,
    id: Int
) {
    Screen(
        viewModel = viewModel { CharacterDetailsViewModel() },
        navController = navController
    ) { state, viewModel ->

        LaunchedEffect(key1 = Unit) {
            viewModel.init(characterId = id)
        }

        Content(
            state = state,
            onClickBack = navController::popBackStack,
            onAction = viewModel::handleAction
        )

    }

}

@Composable
private fun Content(
    state: CharacterDetailsState = CharacterDetailsState.Loading,
    onAction: (CharacterDetailsAction) -> Unit = { },
    onClickBack: () -> Unit = { }
) = Box(
    modifier = Modifier
        .fillMaxSize()
        .padding(),
    contentAlignment = Alignment.Center
) {

    BackArrow(
        modifier = Modifier
            .align(Alignment.TopStart)
            .zIndex(1f),
        onClick = onClickBack
    )

    Crossfade(targetState = state) {
        when (it) {
            is CharacterDetailsState.Error -> ErrorView(error = it.message)
            is CharacterDetailsState.Loaded -> CharacterDetailsContent(
                state = it,
                onAction = onAction
            )
            CharacterDetailsState.Loading -> {
                /** TODO: Could display a Loading Animation */
            }
        }
    }
}


@Composable
private fun ErrorView(error: String) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = error,
        textAlign = TextAlign.Center,
        color = PrimaryColor,
        fontSize = 32.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 36.sp
    )
}


private object CharacterDetailsContent {

    @Composable
    operator fun invoke(
        state: CharacterDetailsState.Loaded,
        onAction: (CharacterDetailsAction) -> Unit
    ) {

        var offsetY by remember {
            mutableFloatStateOf(0f)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Header(
                state = state,
                offsetY = offsetY
            )

            LazyColumn {
                itemsIndexed(state.episodes) { index, episode ->
                    if (index == 0) {
                        Box(modifier = Modifier.onGloballyPositioned { offsetY = it.positionInParent().y })
                    }
                    
                    
                    EpisodeCard(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                onAction(CharacterDetailsAction.SelectedEpisode(episode))
                            },
                        episode = episode
                    )

                }

            }

        }


    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun Header(
        state: CharacterDetailsState.Loaded,
        offsetY: Float
    ) {

        val density = LocalDensity.current

        val additionalHeight: Dp = with(density) { offsetY.toDp() }

        val animatedHeight by animateDpAsState(targetValue = 200.dp + additionalHeight)

        Box(
            modifier = Modifier.height(animatedHeight)
        ) {
            Avatar(url = state.avatarUrl)

            Column(
                modifier = Modifier
                    .background(SurfaceColor.copy(alpha = 0.3f))
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                Text(
                    modifier = Modifier
                        .background(SurfaceColor, RoundedCornerShape(4.dp))
                        .basicMarquee(iterations = Int.MAX_VALUE)
                        .padding(8.dp),
                    text = state.name,
                    fontSize = 21.sp,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center
                )

                AdditionalInfo(
                    gender = state.gender,
                    status = state.status,
                    location = state.location
                )

            }
        }
    }


    @Composable
    private fun AdditionalInfo(
        gender: CharacterGender,
        status: CharacterStatus,
        location: String
    ) = Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Spacer(Modifier.width(8.dp))

        IconWithImage(
            modifier = Modifier.weight(1f),
            imageVector = gender.imageVector, text = gender.text
        )

        Spacer(Modifier.width(16.dp))

        IconWithImage(
            modifier = Modifier.weight(1f),
            imageVector = Icons.Rounded.Home, text = location
        )

        Spacer(Modifier.width(16.dp))

        IconWithImage(
            modifier = Modifier.weight(1f),
            imageVector = status.imageVector, text = status.text
        )

        Spacer(Modifier.width(8.dp))

    }

    @Composable
    private fun EpisodeCard(
        modifier: Modifier, episode: Episode
    ) =
        Column(
            modifier = modifier
                .shadow(1.dp, spotColor = PrimaryColor)
                .background(SurfaceColor)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {

            Text(text = episode.airDate, fontSize = 11.sp)

            Text(
                text = "${episode.episode} - ${episode.name}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis, fontSize = 13.sp
            )

        }


}

@Preview
@Composable
private fun CharacterDetailsPreview() = PreviewContent {
    Content()
}

