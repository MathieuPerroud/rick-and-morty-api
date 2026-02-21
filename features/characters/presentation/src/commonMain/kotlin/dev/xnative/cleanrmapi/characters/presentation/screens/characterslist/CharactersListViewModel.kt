package dev.xnative.cleanrmapi.characters.presentation.screens.characterslist

import dev.xnative.cleanrmapi.characters.presentation.screens.characterslist.CharactersListContracts.UiAction
import dev.xnative.cleanrmapi.characters.presentation.screens.characterslist.CharactersListContracts.UiState
import dev.xnative.cleanrmapi.presentation.StoreViewModel

/**
 * ViewModel for [CharactersListScreen].
 *
 * It delegates business orchestration to [CharactersListStore] through typed store actions.
 */
class CharactersListViewModel :
    StoreViewModel<UiState, CharactersListStore, UiAction>(
        store = CharactersListStore()
    )
