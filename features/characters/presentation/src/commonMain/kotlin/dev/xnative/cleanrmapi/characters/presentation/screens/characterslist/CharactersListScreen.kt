package dev.xnative.cleanrmapi.characters.presentation.screens.characterslist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.xnative.cleanrmapi.characters.presentation.components.ListOfCharactersComponent
import dev.xnative.cleanrmapi.characters.presentation.screens.characterslist.CharactersListContracts.UiAction
import dev.xnative.cleanrmapi.characters.presentation.screens.characterslist.CharactersListContracts.UiState
import dev.xnative.cleanrmapi.characters.presentation.screens.characterslist.actions.LoadMoreCharacters
import dev.xnative.cleanrmapi.characters.presentation.screens.characterslist.actions.NavigateToCharacterDetails
import dev.xnative.cleanrmapi.domain.character.models.CharacterPreview
import dev.xnative.cleanrmapi.presentation.composables.PreviewContent
import dev.xnative.cleanrmapi.presentation.composables.Screen
import dev.xnative.cleanrmapi.presentation.navigation.NavScreen
import kotlinx.serialization.Serializable

@Serializable
data object CharactersListScreen: NavScreen {

    @Composable
    operator fun invoke() = MainComponent()

    /**
     * Hosts the Characters List in a portrait navigation context.
     *
     * The screen owns the ViewModel lifecycle and translates component-level actions into
     * screen-level [CharactersListContracts.UiAction] handled by the store.
     *
     * Action mapping in this screen:
     * - [dev.xnative.cleanrmapi.characters.presentation.components.ListOfCharactersComponent.OnCharacterClicked] -> [NavigateToCharacterDetails]
     * - [dev.xnative.cleanrmapi.characters.presentation.components.ListOfCharactersComponent.OnReachedBottom] -> [LoadMoreCharacters]
     *
     * This keeps [dev.xnative.cleanrmapi.characters.presentation.components.ListOfCharactersComponent] reusable while the screen remains responsible for
     * navigation and orchestration decisions.
     */
    @Composable
    override fun MainComponent(modifier: Modifier) {

        Screen(
            viewModel = viewModel { CharactersListViewModel() }
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
        ListOfCharactersComponent(
            uiState = uiState.listOfCharactersState,
            onAction = { action ->
                when (action) {
                    is ListOfCharactersComponent.OnCharacterClicked -> {
                        onAction(NavigateToCharacterDetails(action.character))
                    }

                    ListOfCharactersComponent.OnReachedBottom -> {
                        onAction(LoadMoreCharacters)
                    }
                }
            }
        ).MainComponent(modifier = modifier)
    }

    @Preview
    @Composable
    private fun CharactersListLoadedPreview() = PreviewContent {
        Content(
            uiState = UiState(
                listOfCharactersState = ListOfCharactersComponent.Loaded(
                    characters = previewCharacters
                )
            ),
            onAction = { }
        )
    }

    @Preview
    @Composable
    private fun CharactersListLoadingPreview() = PreviewContent {
        Content(
            uiState = UiState(
                listOfCharactersState = ListOfCharactersComponent.Loading
            ),
            onAction = { }
        )
    }

    @Preview
    @Composable
    private fun CharactersListErrorPreview() = PreviewContent {
        Content(
            uiState = UiState(
                listOfCharactersState = ListOfCharactersComponent.Error(
                    message = "Unable to load characters."
                )
            ),
            onAction = { }
        )
    }

    private val previewCharacters = listOf(
        CharacterPreview(1, "Rick Sanchez", "Human", "", ""),
        CharacterPreview(2, "Morty Smith", "Human", "", ""),
        CharacterPreview(3, "Summer Smith", "Human", "", ""),
        CharacterPreview(4, "Beth Smith", "Human", "", "")
    )
}
