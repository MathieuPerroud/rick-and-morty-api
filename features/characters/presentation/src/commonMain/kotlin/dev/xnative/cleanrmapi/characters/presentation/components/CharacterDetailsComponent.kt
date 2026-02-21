package dev.xnative.cleanrmapi.characters.presentation.components

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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.xnative.cleanrmapi.characters.domain.models.CharacterGender
import dev.xnative.cleanrmapi.characters.domain.models.CharacterStatus
import dev.xnative.cleanrmapi.characters.presentation.extensions.imageVector
import dev.xnative.cleanrmapi.characters.presentation.extensions.text
import dev.xnative.cleanrmapi.characters.presentation.components.CharacterDetailsComponent.Loaded
import dev.xnative.cleanrmapi.characters.presentation.components.CharacterDetailsComponent.OnEpisodeClicked
import dev.xnative.cleanrmapi.domain.episode.models.EpisodePreview
import dev.xnative.cleanrmapi.presentation.UiComponent
import dev.xnative.cleanrmapi.presentation.composables.Avatar
import dev.xnative.cleanrmapi.presentation.composables.IconWithImage
import dev.xnative.cleanrmapi.presentation.composables.PreviewContent
import dev.xnative.cleanrmapi.presentation.theme.BackgroundColor
import dev.xnative.cleanrmapi.presentation.theme.PrimaryColor
import dev.xnative.cleanrmapi.presentation.theme.SurfaceColor

/**
 * Stateless-infrastructure / stateful-UI component that renders character details content.
 *
 * The component owns its local UI contract ([UiState] and [UiAction]) and does not perform
 * screen-level business decisions. It only emits user intent to its caller.
 *
 * Specific features:
 * - Crossfades between loading and loaded states.
 * - Displays a hero header with avatar, name, gender, location and status.
 * - Animates header height based on list scroll offset.
 * - Renders related episodes as clickable cards.
 *
 * User interactions:
 * - Clicking an episode emits [OnEpisodeClicked].
 *
 * Integration note:
 * - The hosting screen decides how to interpret [OnEpisodeClicked] (navigate in portrait,
 *   update a side panel in a dashboard, etc.).
 */
class CharacterDetailsComponent(
    private val uiState: UiState,
    private val onAction: (UiAction) -> Unit
) : UiComponent {

    /**
     * Visual states rendered by the details component.
     */
    interface UiState
    object Loading : UiState

    data class Loaded(
        val name: String,
        val avatarUrl: String,
        val episodes: List<EpisodePreview>,
        val status: CharacterStatus,
        val gender: CharacterGender,
        val origin: String,
        val location: String,
    ) : UiState

    /**
     * Intents emitted by the details component to its host.
     */
    sealed interface UiAction

    data class OnEpisodeClicked(val episode: EpisodePreview) : UiAction

    @Composable
    override fun MainComponent(
        modifier: Modifier
    ) = Box(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundColor),
        contentAlignment = Alignment.Center
    ) {

        Crossfade(targetState = uiState) {
            when (it) {
                is Loaded -> LoadedContent(
                    state = it,
                    onAction = onAction
                )
                Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

}


private val previewEpisodes = listOf(
    EpisodePreview(id = 1, name = "Pilot", airDate = "December 2, 2013", episode = "S01E01"),
    EpisodePreview(id = 2, name = "Lawnmower Dog", airDate = "December 9, 2013", episode = "S01E02"),
    EpisodePreview(id = 3, name = "Anatomy Park", airDate = "December 16, 2013", episode = "S01E03")
)

private object LoadedContent {

    @Composable
    operator fun invoke(
        state: Loaded,
        onAction: (CharacterDetailsComponent.UiAction) -> Unit
    ) {

        var offsetY by remember {
            mutableFloatStateOf(0f)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
        ) {

            Header(
                state = state,
                offsetY = offsetY
            )

            LazyColumn {
                itemsIndexed(state.episodes) { index, episode ->
                    if (index == 0) {
                        Box(modifier = Modifier.onGloballyPositioned {
                            offsetY = it.positionInParent().y
                        })
                    }


                    EpisodeCard(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                onAction(OnEpisodeClicked(episode))
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
        state: Loaded,
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
        modifier: Modifier, episode: EpisodePreview
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

    @Preview
    @Composable
    private fun CharacterDetailsComponentLoadedPreview() = PreviewContent {
        CharacterDetailsComponent(
            uiState = Loaded(
                name = "Rick Sanchez",
                avatarUrl = "",
                episodes = previewEpisodes,
                status = CharacterStatus.Alive,
                gender = CharacterGender.Male,
                origin = "Earth (C-137)",
                location = "The Citadel"
            ),
            onAction = { }
        ).MainComponent()
    }

    @Preview
    @Composable
    private fun CharacterDetailsComponentLoadingPreview() = PreviewContent {
        CharacterDetailsComponent(
            uiState = CharacterDetailsComponent.Loading,
            onAction = { }
        ).MainComponent()
    }

}
