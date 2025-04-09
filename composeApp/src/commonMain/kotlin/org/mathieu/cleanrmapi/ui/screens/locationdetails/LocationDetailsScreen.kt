package org.mathieu.cleanrmapi.ui.screens.locationdetails

import CharacterCard
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.mathieu.cleanrmapi.ui.core.composables.BackArrow
import org.mathieu.cleanrmapi.ui.core.composables.PreviewContent
import org.mathieu.cleanrmapi.ui.core.composables.Screen
import org.mathieu.cleanrmapi.ui.core.theme.PrimaryColor
import org.mathieu.cleanrmapi.ui.core.theme.SurfaceColor
import org.mathieu.cleanrmapi.ui.screens.episodedetails.LocationDetailsAction
import org.mathieu.cleanrmapi.ui.screens.episodedetails.LocationDetailsState
import org.mathieu.cleanrmapi.ui.screens.episodedetails.LocationDetailsViewModel

@Composable
fun LocationDetailsScreen(
    navController: NavController,
    id: Int
) {
    Screen(
        viewModel = viewModel { LocationDetailsViewModel() },
        navController = navController
    ) { state, viewModel ->

        LaunchedEffect(Unit) {
            viewModel.init(locationId = id)
        }

        LocationContent(
            state = state,
            onClickBack = navController::popBackStack,
            onAction = viewModel::handleAction
        )
    }
}

@Composable
private fun LocationContent(
    state: LocationDetailsState = LocationDetailsState.Loading,
    onClickBack: () -> Unit = {},
    onAction: (LocationDetailsAction) -> Unit = { },
) {
    var showLoader by remember { mutableStateOf(true) }

    LaunchedEffect(state) {
        if (state is LocationDetailsState.Loaded) {
            showLoader = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
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
                is LocationDetailsState.Error -> ErrorView(error = it.message)
                is LocationDetailsState.Loaded -> {
                    if (showLoader) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = PrimaryColor
                        )
                    } else {
                        LocationDetailsBody(
                            state = it,
                            onAction = onAction
                        )
                    }
                }
                LocationDetailsState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = PrimaryColor
                    )
                }
                else -> {}
            }
        }
    }
}

@Composable
private fun LocationDetailsBody(state: LocationDetailsState.Loaded, onAction: (LocationDetailsAction) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
    ) {
        LocationHeader(state)

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(state.residents) { character ->
                CharacterCard(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            onAction(LocationDetailsAction.SelectedCharacter(character))
                        },
                    character = character
                )
            }
        }
    }
}

@Composable
private fun LocationHeader(state: LocationDetailsState.Loaded) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .background(SurfaceColor)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Home,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = PrimaryColor
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = state.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Type: ${state.type}", fontSize = 16.sp)
                Text(text = "Dimension: ${state.dimension}", fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun ErrorView(error: String) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = error,
        color = Color.Red,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 24.sp
    )
}

@Preview
@Composable
private fun LocationDetailsPreview() = PreviewContent {
    LocationContent()
}
