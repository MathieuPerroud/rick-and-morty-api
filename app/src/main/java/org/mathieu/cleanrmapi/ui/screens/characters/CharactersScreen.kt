package org.mathieu.cleanrmapi.ui.screens.characters

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.mathieu.cleanrmapi.domain.models.character.Character
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.composables.PreviewContent
import org.mathieu.cleanrmapi.ui.core.navigate
import org.mathieu.cleanrmapi.ui.core.theme.Purple40

private typealias UIState = CharactersState
private typealias UIAction = CharactersAction

@Composable
fun CharactersScreen(navController: NavController) {
    val viewModel: CharactersViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.events
            .onEach { event ->
                if (event is Destination.CharacterDetails)
                    navController.navigate(destination = event)
            }.collect()
    }

    CharactersContent(
        state = state,
        onAction = viewModel::handleAction
    )

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun CharactersContent(
    state: UIState = UIState(),
    onAction: (UIAction) -> Unit = { }
) = Scaffold(topBar = {
    Text(
        modifier = Modifier
            .background(Purple40)
            .padding(16.dp)
            .fillMaxWidth(),
        text = "Characters",
        textAlign = TextAlign.Center,
        color = Color.White,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium
    )
}) { paddingValues ->

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(targetState = state.error != null, label = "") {
            state.error?.let { error ->
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = error,
                    textAlign = TextAlign.Center,
                    color = Purple40,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 36.sp
                )
            } ?: LazyColumn {
                itemsIndexed(state.characters) { index, character ->
                    CharacterCard(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                onAction(CharactersAction.SelectedCharacter(character))
                            },
                        character = character
                    )

                    if (index == state.characters.size - 1) {
                        onAction(CharactersAction.ReachedTheBottomOfTheList)
                    }
                }

            }
        }
    }

}

@Composable
private fun CharacterCard(
    modifier: Modifier, character: Character
) =
    Row(
        modifier = modifier
            .shadow(5.dp)
            .background(Color.White)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {

        SubcomposeAsyncImage(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape),
            model = character.avatarUrl,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(text = character.name)

    }



@Preview
@Composable
private fun CharactersPreview() = PreviewContent {
    CharactersContent()
}

