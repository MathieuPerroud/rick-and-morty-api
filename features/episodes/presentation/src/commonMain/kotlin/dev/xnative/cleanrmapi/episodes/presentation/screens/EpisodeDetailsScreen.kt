package dev.xnative.cleanrmapi.episodes.presentation.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.xnative.cleanrmapi.navigation.Destination
import dev.xnative.cleanrmapi.navigation.Router
import dev.xnative.cleanrmapi.presentation.composables.BackArrow
import dev.xnative.cleanrmapi.presentation.composables.CharacterCard
import dev.xnative.cleanrmapi.presentation.composables.PreviewContent
import dev.xnative.cleanrmapi.presentation.composables.Screen
import dev.xnative.cleanrmapi.presentation.theme.PrimaryColor
import dev.xnative.cleanrmapi.presentation.theme.SurfaceColor
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeDetails(val episodeId: Int) : Destination() {

    /**
     * Displays the Episode Details screen for a selected episode and lists all related characters.
     *
     * Specific features:
     * - Shows an episode header with air date, code, and name.
     * - Displays episode characters in a two-column grid.
     * - Keeps a back button available at the top of the screen.
     *
     * Error handling:
     * - If episode data cannot be retrieved, an error message is displayed.
     *
     * User interaction:
     * - The user can navigate back from the current episode.
     * - The user can tap a character card to navigate to that character details screen.
     */
    @Composable
    override fun Screen(router: Router) {

        Screen(
            viewModel = viewModel { EpisodeDetailsViewModel(episodeId) },
            router = router
        ) { state, viewModel ->

            Content(
                state = state,
                onClickBack = router::navigateBack,
                onAction = viewModel::handleAction
            )

        }

    }

    @Composable
    private fun Content(
        state: EpisodeDetailsState = EpisodeDetailsState.Loading,
        onAction: (EpisodeDetailsAction) -> Unit = { },
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
                is EpisodeDetailsState.Error -> ErrorView(error = it.message)
                is EpisodeDetailsState.Loaded -> CharacterDetailsContent(
                    state = it,
                    onAction = onAction
                )

                EpisodeDetailsState.Loading -> {
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
            state: EpisodeDetailsState.Loaded,
            onAction: (EpisodeDetailsAction) -> Unit = { }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Header(state = state)

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {


                    items(
                        state.characters
                    ) { character ->

                        CharacterCard(
                            modifier = Modifier
                                .clickable {
                                    onAction(EpisodeDetailsAction.SelectedCharacter(character))
                                },
                            character = character
                        )

                    }

                }

            }


        }


        @OptIn(ExperimentalFoundationApi::class)
        @Composable
        private fun Header(state: EpisodeDetailsState.Loaded) {

            Column(
                modifier = Modifier
                    .background(SurfaceColor)
                    .fillMaxWidth()
                    .padding(start = 48.dp)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {

                Text(text = state.airDate, fontSize = 11.sp)

                Text(
                    modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
                    text = "${state.episode} - ${state.name}",
                    maxLines = 1
                )

            }

        }

    }

    @Preview
    @Composable
    private fun CharacterDetailsPreview() = PreviewContent {
        Content()
    }

}
