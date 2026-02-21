package dev.xnative.cleanrmapi.characters.presentation.screens

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.xnative.cleanrmapi.characters.presentation.screens.characterdetails.CharacterDetailsScreen
import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.CharactersDashboardContracts.NoCharacterSelected
import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.CharactersDashboardScreen
import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.CharactersDashboardViewModel
import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.actions.ShowCharacterDetails
import dev.xnative.cleanrmapi.characters.presentation.screens.characterslist.CharactersListScreen
import dev.xnative.cleanrmapi.characters.presentation.screens.characterslist.CharactersListViewModel
import dev.xnative.cleanrmapi.characters.presentation.screens.characterslist.actions.NavigateToCharacterDetails
import dev.xnative.cleanrmapi.domain.character.models.CharacterPreview
import dev.xnative.cleanrmapi.navigation.Router
import dev.xnative.cleanrmapi.presentation.localproviders.LocalRouterProvider
import dev.xnative.cleanrmapi.presentation.navigation.NavScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

/**
 * Entry screen for the Characters feature.
 *
 * This screen hosts an internal Navigation3 back stack dedicated to adaptive behavior:
 * - `Dashboard` in horizontal mode.
 * - `List` and optional `Details` in vertical mode.
 *
 * The parent app router is still used to leave the feature.
 */
@Serializable
data object CharactersEntryScreen : NavScreen {

    @Composable
    override fun MainComponent(modifier: Modifier) {
        val entryViewModel = viewModel { CharactersEntryViewModel() }
        val state by entryViewModel.state.collectAsState()
        val parentRouter = LocalRouterProvider.current

        // Delegate parent navigation except back, which can be consumed by the internal stack first.
        val localRouter = remember(entryViewModel, parentRouter) {
            object : Router {
                override fun navigateTo(vararg screens: NavKey) {
                    parentRouter.navigateTo(*screens)
                }

                override fun navigateBack() {
                    entryViewModel.navigateBack(parentRouter::navigateBack)
                }

                override fun getActiveStatusForScreen(screen: NavKey): Flow<Boolean> {
                    return parentRouter.getActiveStatusForScreen(screen)
                }
            }
        }

        BoxWithConstraints(modifier = modifier.fillMaxSize()) {
            val isHorizontalLayout = maxWidth > maxHeight

            LaunchedEffect(isHorizontalLayout) {
                entryViewModel.onLayoutChanged(isHorizontalLayout)
            }

            CompositionLocalProvider(LocalRouterProvider provides localRouter) {
                NavDisplay(
                    backStack = state.backStack,
                    onBack = {
                        entryViewModel.navigateBack(parentRouter::navigateBack)
                    },
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    entryProvider = { key ->
                        NavEntry(key) {
                            when (key) {
                                CharactersEntryDestination.Dashboard -> CharactersDashboardDestination(
                                    selectedCharacter = state.selectedCharacter,
                                    onSelectedCharacter = entryViewModel::onCharacterSelected
                                )

                                CharactersEntryDestination.List -> CharactersListDestination(
                                    onSelectedCharacter = entryViewModel::onCharacterSelected
                                )

                                is CharactersEntryDestination.Details -> CharacterDetailsScreen(
                                    characterId = key.characterId
                                ).MainComponent()
                            }
                        }
                    }
                )
            }
        }
    }

    @Composable
    private fun CharactersDashboardDestination(
        selectedCharacter: CharacterPreview?,
        onSelectedCharacter: (CharacterPreview) -> Unit
    ) {
        val viewModel = viewModel { CharactersDashboardViewModel() }
        val uiState by viewModel.state.collectAsState()

        // When returning from portrait with a selected character, hydrate dashboard detail pane.
        LaunchedEffect(uiState.detailSection, selectedCharacter?.id) {
            if (selectedCharacter != null && uiState.detailSection is NoCharacterSelected) {
                viewModel.handleAction(ShowCharacterDetails(selectedCharacter))
            }
        }

        CharactersDashboardScreen.Content(
            uiState = uiState,
            onAction = { action ->
                if (action is ShowCharacterDetails) {
                    onSelectedCharacter(action.character)
                }
                viewModel.handleAction(action)
            },
            modifier = Modifier.fillMaxSize()
        )
    }

    @Composable
    private fun CharactersListDestination(
        onSelectedCharacter: (CharacterPreview) -> Unit
    ) {
        val viewModel = viewModel { CharactersListViewModel() }
        val uiState by viewModel.state.collectAsState()

        CharactersListScreen.Content(
            uiState = uiState,
            onAction = { action ->
                when (action) {
                    is NavigateToCharacterDetails -> onSelectedCharacter(action.character)
                    else -> viewModel.handleAction(action)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
