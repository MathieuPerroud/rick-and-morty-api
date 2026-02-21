package dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.CharacterDetailsContracts.UiAction
import dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.CharacterDetailsContracts.UiState
import dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.actions.NavigateToEpisodeDetails
import dev.xnative.cleanrmapi.characters.domain.models.CharacterGender
import dev.xnative.cleanrmapi.characters.domain.models.CharacterStatus
import dev.xnative.cleanrmapi.characters.presentation.components.CharacterDetailsComponent
import dev.xnative.cleanrmapi.domain.episode.models.EpisodePreview
import dev.xnative.cleanrmapi.presentation.navigation.NavScreen
import dev.xnative.cleanrmapi.presentation.composables.BackArrow
import dev.xnative.cleanrmapi.presentation.composables.PreviewContent
import dev.xnative.cleanrmapi.presentation.composables.Screen
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDetailsScreen(val characterId: Int) : NavScreen {

    /**
     * Hosts Character Details in a portrait navigation context.
     *
     * In this architecture, the screen owns the ViewModel and interprets component output actions
     * as screen-level actions. The underlying [dev.xnative.cleanrmapi.characters.presentation.components.CharacterDetailsComponent] remains reusable and only
     * emits UI-intent events.
     *
     * Action mapping in this screen:
     * - [dev.xnative.cleanrmapi.characters.presentation.components.CharacterDetailsComponent.OnEpisodeClicked] is converted to [NavigateToEpisodeDetails].
     * - Back interaction triggers [dev.xnative.cleanrmapi.navigation.Router.navigateBack].
     *
     * In portrait mode this action flow typically resolves to navigation from list to detail, then
     * from character detail to episode detail through the screen ViewModel event handling.
     */
    @Composable
    override fun MainComponent(modifier: Modifier) {
        Screen(
            viewModel = viewModel { CharacterDetailsViewModel(characterId = characterId) }
        ) { router, uiState, viewModel ->
            Content(
                uiState = uiState,
                onClickBack = router::navigateBack,
                onAction = viewModel::handleAction
            )
        }
    }

    @Composable
    private fun Content(
        uiState: UiState = UiState(),
        onAction: (UiAction) -> Unit = { },
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

        CharacterDetailsComponent(
            uiState = uiState.characterDetailsState,
            onAction = { action ->
                when (action) {
                    is CharacterDetailsComponent.OnEpisodeClicked ->
                        onAction(NavigateToEpisodeDetails(action.episode.id))
                }
            }
        ).MainComponent()

    }

    @Preview
    @Composable
    private fun CharacterDetailsLoadingPreview() = PreviewContent {
        Content(
            uiState = UiState(
                characterDetailsState = CharacterDetailsComponent.Loading
            )
        )
    }

    @Preview
    @Composable
    private fun CharacterDetailsLoadedPreview() = PreviewContent {
        Content(
            uiState = UiState(
                characterDetailsState = CharacterDetailsComponent.Loaded(
                    name = "Rick Sanchez",
                    avatarUrl = "",
                    episodes = previewEpisodes,
                    status = CharacterStatus.Alive,
                    gender = CharacterGender.Male,
                    origin = "Earth (C-137)",
                    location = "The Citadel"
                )
            )
        )
    }
}

private val previewEpisodes = listOf(
    EpisodePreview(id = 1, name = "Pilot", airDate = "December 2, 2013", episode = "S01E01"),
    EpisodePreview(id = 2, name = "Lawnmower Dog", airDate = "December 9, 2013", episode = "S01E02"),
    EpisodePreview(id = 3, name = "Anatomy Park", airDate = "December 16, 2013", episode = "S01E03")
)
