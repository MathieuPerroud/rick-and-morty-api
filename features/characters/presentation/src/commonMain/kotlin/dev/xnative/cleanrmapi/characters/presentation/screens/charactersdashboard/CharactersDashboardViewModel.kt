package dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard

import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.CharactersDashboardContracts.UiAction
import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.CharactersDashboardContracts.UiState
import dev.xnative.cleanrmapi.presentation.StoreViewModel

/**
 * ViewModel for the horizontal dashboard experience.
 */
class CharactersDashboardViewModel : StoreViewModel<UiState, CharactersDashboardStore, UiAction>(
    store = CharactersDashboardStore()
)
