package dev.xnative.cleanrmapi.characters.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.xnative.cleanrmapi.domain.character.models.CharacterPreview
import dev.xnative.cleanrmapi.presentation.UiComponent
import dev.xnative.cleanrmapi.presentation.composables.CharacterCard
import dev.xnative.cleanrmapi.presentation.composables.PreviewContent
import dev.xnative.cleanrmapi.presentation.theme.BackgroundColor
import dev.xnative.cleanrmapi.presentation.theme.PrimaryColor
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

/**
 * Renders a paginated list of characters as a reusable UI component.
 *
 * The component is intentionally UI-focused:
 * - it owns a local UI contract through [UiState] and [UiAction],
 * - it emits user intents upward,
 * - it does not execute screen-level navigation or business orchestration.
 *
 * Specific features:
 * - Displays loading, error, and loaded states.
 * - Uses a grid layout of [CharacterCard].
 * - Detects near-bottom scrolling and emits [OnReachedBottom] for pagination.
 * - Shows inline loading and inline loading error for "load more" operations.
 *
 * User interactions:
 * - Tapping a character emits [OnCharacterClicked].
 * - Scrolling near the end emits [OnReachedBottom].
 */
class ListOfCharactersComponent(
    private val uiState: UiState,
    private val onAction: (UiAction) -> Unit
) : UiComponent {

    sealed interface UiState
    data object Loading : UiState
    data class Error(val message: String) : UiState

    data class Loaded(
        val characters: List<CharacterPreview>,
        val isLoadingMore: Boolean = false,
        val loadingMoreError: String? = null
    ) : UiState

    sealed interface UiAction
    data class OnCharacterClicked(val character: CharacterPreview) : UiAction
    data object OnReachedBottom : UiAction

    @Composable
    override fun MainComponent(
        modifier: Modifier
    ) = Box(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        when (val state = uiState) {
            is Error -> ErrorView(
                error = state.message
            )
            Loading -> Loading()
            is Loaded -> LoadedContent(
                characters = state.characters,
                isLoadingMore = state.isLoadingMore,
                loadingMoreError = state.loadingMoreError,
                onReachedBottom = { onAction(OnReachedBottom) },
                onClickedCharacter = { character ->
                    onAction(OnCharacterClicked(character))
                }
            )
        }
    }
}

@Preview
@Composable
private fun ListOfCharactersLoadedPreview() = PreviewContent {
    ListOfCharactersComponent(
        uiState = ListOfCharactersComponent.Loaded(
            characters = previewCharacters
        ),
        onAction = { }
    ).MainComponent()
}

@Preview
@Composable
private fun ListOfCharactersLoadingPreview() = PreviewContent {
    ListOfCharactersComponent(
        uiState = ListOfCharactersComponent.Loading,
        onAction = { }
    ).MainComponent()
}

@Preview
@Composable
private fun ListOfCharactersErrorPreview() = PreviewContent {
    ListOfCharactersComponent(
        uiState = ListOfCharactersComponent.Error("Unable to load characters."),
        onAction = { }
    ).MainComponent()
}

private val previewCharacters = listOf(
    CharacterPreview(1, "Rick Sanchez", "Human", "", ""),
    CharacterPreview(2, "Morty Smith", "Human", "", ""),
    CharacterPreview(3, "Summer Smith", "Human", "", ""),
    CharacterPreview(4, "Beth Smith", "Human", "", "")
)

@Composable
private fun Loading(modifier: Modifier = Modifier) = Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    CircularProgressIndicator()
}

@Composable
private fun ErrorView(error: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(16.dp),
        text = error,
        textAlign = TextAlign.Center,
        color = PrimaryColor,
        fontSize = 32.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 36.sp
    )
}

@Composable
private fun LoadedContent(
    modifier: Modifier = Modifier,
    characters: List<CharacterPreview>,
    isLoadingMore: Boolean,
    loadingMoreError: String?,
    onReachedBottom: () -> Unit,
    onClickedCharacter: (CharacterPreview) -> Unit
) {
    val gridState = rememberLazyGridState()

    ObserveReachedBottom(
        gridState = gridState,
        onReachedBottom = onReachedBottom
    )

    LazyVerticalGrid(
        modifier = modifier,
        state = gridState,
        columns = GridCells.Fixed(2)
    ) {
        itemsIndexed(characters) { _, character ->
            CharacterCard(
                modifier = Modifier.clickable {
                    onClickedCharacter(character)
                },
                character = character
            )
        }

        if (isLoadingMore) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }

        loadingMoreError?.let { error ->
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = error,
                    color = PrimaryColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun ObserveReachedBottom(
    gridState: LazyGridState,
    buffer: Int = 4,
    onReachedBottom: () -> Unit
) {
    LaunchedEffect(gridState, buffer) {
        snapshotFlow {
            val layout = gridState.layoutInfo
            val totalItems = layout.totalItemsCount
            val lastVisibleIndex = layout.visibleItemsInfo.lastOrNull()?.index ?: 0
            totalItems > 0 && lastVisibleIndex >= (totalItems - 1 - buffer)
        }
            .distinctUntilChanged()
            .filter { it }
            .collect { onReachedBottom() }
    }
}
