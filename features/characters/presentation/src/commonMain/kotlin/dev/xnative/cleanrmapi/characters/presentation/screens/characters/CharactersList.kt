package dev.xnative.cleanrmapi.characters.presentation.screens.characters

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cleanrmapiudf.core.presentation.generated.resources.Res
import cleanrmapiudf.core.presentation.generated.resources.characters
import dev.xnative.cleanrmapi.navigation.Destination
import dev.xnative.cleanrmapi.navigation.Router
import dev.xnative.cleanrmapi.presentation.composables.CharacterCard
import dev.xnative.cleanrmapi.presentation.composables.PreviewContent
import dev.xnative.cleanrmapi.presentation.composables.Screen
import dev.xnative.cleanrmapi.presentation.theme.OnPrimaryColor
import dev.xnative.cleanrmapi.presentation.theme.PrimaryColor
import org.jetbrains.compose.resources.stringResource
import dev.xnative.cleanrmapi.characters.presentation.screens.characters.CharactersContracts.*
import kotlinx.serialization.Serializable

@Serializable
data object CharactersList : Destination() {

    /**
     * Displays the Characters List screen as the main entry point for character browsing.
     *
     * Specific features:
     * - Shows a two-column grid of character cards.
     * - Triggers pagination when the user reaches the bottom of the currently loaded list.
     * - Keeps a bottom bar title visible for quick screen identification.
     *
     * Error handling:
     * - If loading fails, an error message is shown instead of the grid content.
     *
     * User interaction:
     * - The user can open character details by tapping a character card.
     * - Scrolling to the end of the grid requests the next page.
     */
    @Composable
    override fun Screen(
        router: Router
    ) {

        Screen(
            viewModel = viewModel { CharactersViewModel() },
            router = router
        ) { state, viewModel ->

            Content(
                state = state,
                handleAction = viewModel::handleAction
            )
        }

    }


    @Composable
    private fun Content(
        state: UiState = UiState(),
        handleAction: UiAction.() -> Unit = { }
    ) = Scaffold(bottomBar = {
        Text(
            modifier = Modifier
                .background(PrimaryColor)
                .padding(16.dp)
                .fillMaxWidth(),
            text = stringResource(Res.string.characters),
            textAlign = TextAlign.Center,
            color = OnPrimaryColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter
        ) {
            AnimatedContent(targetState = state.error != null, label = "") {
                state.error?.let { error ->
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = error,
                        textAlign = TextAlign.Center,
                        color = PrimaryColor,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 36.sp
                    )
                } ?: LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {

                    itemsIndexed(state.characters) { index, character ->
                        CharacterCard(
                            modifier = Modifier
                                .clickable {
                                    SelectedCharacter(character).handleAction()
                                },
                            character = character
                        )

                        if (index == state.characters.size - 1) {
                            handleAction(ReachedTheBottomOfTheList)
                        }
                    }

                }
            }
        }

    }

    @Preview
    @Composable
    private fun CharactersPreview() = PreviewContent {
        Content()
    }
}
