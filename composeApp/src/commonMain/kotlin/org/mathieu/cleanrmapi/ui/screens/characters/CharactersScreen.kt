package org.mathieu.cleanrmapi.ui.screens.characters

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cleanrmapiudf.composeapp.generated.resources.Res
import cleanrmapiudf.composeapp.generated.resources.characters
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.mathieu.cleanrmapi.ui.core.composables.CharacterCard
import org.mathieu.cleanrmapi.ui.core.composables.PreviewContent
import org.mathieu.cleanrmapi.ui.core.composables.Screen
import org.mathieu.cleanrmapi.ui.core.theme.OnPrimaryColor
import org.mathieu.cleanrmapi.ui.core.theme.PrimaryColor
import org.mathieu.cleanrmapi.ui.screens.characters.CharactersContracts.*

@Composable
fun CharactersScreen(navController: NavController) {

    Screen(
        viewModel = viewModel<CharactersViewModel>(),
        navController = navController
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
            } ?:

            LazyVerticalGrid(
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

