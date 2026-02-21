package dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.xnative.cleanrmapi.characters.presentation.components.CharacterDetailsComponent
import dev.xnative.cleanrmapi.characters.presentation.components.ListOfCharactersComponent
import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.CharactersDashboardContracts.CharacterSelected
import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.CharactersDashboardContracts.NoCharacterSelected
import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.CharactersDashboardContracts.UiAction
import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.CharactersDashboardContracts.UiState
import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.actions.LoadMoreCharacters
import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.actions.NavigateToEpisodeDetails
import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.actions.ShowCharacterDetails
import dev.xnative.cleanrmapi.presentation.navigation.NavScreen
import dev.xnative.cleanrmapi.presentation.composables.Screen
import dev.xnative.cleanrmapi.presentation.theme.PrimaryColor
import dev.xnative.cleanrmapi.presentation.theme.SurfaceColor
import kotlinx.serialization.Serializable

/**
 * Horizontal master-detail screen for the Characters feature.
 *
 * Left pane: reusable [ListOfCharactersComponent].
 * Right pane: placeholder or [CharacterDetailsComponent] for selected character.
 */
@Serializable
data object CharactersDashboardScreen : NavScreen {

    @Composable
    operator fun invoke() = MainComponent()

    @Composable
    override fun MainComponent(modifier: Modifier) {
        Screen(
            viewModel = viewModel {
                CharactersDashboardViewModel()
            }
        ) { _, uiState, viewModel ->
            Content(
                uiState = uiState,
                onAction = viewModel::handleAction,
                modifier = modifier
            )
        }
    }

    @Composable
    fun Content(
        uiState: UiState,
        onAction: (UiAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Row(modifier = modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight()
            ) {
                ListOfCharactersComponent(
                    uiState = uiState.listOfCharactersState,
                    onAction = { action ->
                        when (action) {
                            is ListOfCharactersComponent.OnCharacterClicked -> {
                                onAction(ShowCharacterDetails(action.character))
                            }

                            ListOfCharactersComponent.OnReachedBottom -> {
                                onAction(LoadMoreCharacters)
                            }
                        }
                    }
                ).MainComponent(
                    modifier = Modifier
                        .padding(4.dp)
                        .shadow(elevation = 3.dp, shape = RoundedCornerShape(16.dp))
                        .background(SurfaceColor, shape = RoundedCornerShape(16.dp))
                )
            }

            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .padding(4.dp)
                    .shadow(elevation = 3.dp, shape = RoundedCornerShape(16.dp))
                    .background(SurfaceColor, shape = RoundedCornerShape(16.dp))
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp))
            ) {
                when (val detailSection = uiState.detailSection) {
                    NoCharacterSelected -> EmptyCharacterDetailsPlaceholder(
                        modifier = Modifier.fillMaxSize()
                    )

                    is CharacterSelected -> CharacterDetailsComponent(
                        uiState = detailSection.characterDetailsState,
                        onAction = { action ->
                            when (action) {
                                is CharacterDetailsComponent.OnEpisodeClicked -> {
                                    onAction(NavigateToEpisodeDetails(action.episode.id))
                                }
                            }
                        }
                    ).MainComponent(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
private fun EmptyCharacterDetailsPlaceholder(
    modifier: Modifier = Modifier
) = Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = "Select a character to display details",
        textAlign = TextAlign.Center,
        color = PrimaryColor,
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium
    )
}
