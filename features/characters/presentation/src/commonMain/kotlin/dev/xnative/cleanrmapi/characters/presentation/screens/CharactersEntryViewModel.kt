package dev.xnative.cleanrmapi.characters.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import dev.xnative.cleanrmapi.domain.character.models.CharacterPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

internal enum class LayoutMode {
    Horizontal,
    Vertical
}

internal sealed interface CharactersEntryDestination : NavKey {
    data object Dashboard : CharactersEntryDestination
    data object List : CharactersEntryDestination
    data class Details(val characterId: Int) : CharactersEntryDestination
}

internal data class CharactersEntryUiState(
    val layoutMode: LayoutMode? = null,
    val selectedCharacter: CharacterPreview? = null,
    val backStack: NavBackStack<NavKey> = NavBackStack(CharactersEntryDestination.Dashboard)
)

internal class CharactersEntryViewModel : ViewModel() {

    private val _state = MutableStateFlow(CharactersEntryUiState())
    val state: StateFlow<CharactersEntryUiState>
        get() = _state

    fun onLayoutChanged(isHorizontalLayout: Boolean) {
        val nextLayoutMode = if (isHorizontalLayout) LayoutMode.Horizontal else LayoutMode.Vertical
        val currentState = state.value

        if (currentState.layoutMode == nextLayoutMode) return

        _state.update { it.copy(layoutMode = nextLayoutMode) }

        when (nextLayoutMode) {
            LayoutMode.Horizontal -> {
                setBackStack(CharactersEntryDestination.Dashboard)
            }

            LayoutMode.Vertical -> {
                val selectedCharacterId = state.value.selectedCharacter?.id
                if (selectedCharacterId == null) {
                    setBackStack(CharactersEntryDestination.List)
                } else {
                    setBackStack(
                        CharactersEntryDestination.List,
                        CharactersEntryDestination.Details(selectedCharacterId)
                    )
                }
            }
        }
    }

    fun onCharacterSelected(character: CharacterPreview) {
        _state.update { it.copy(selectedCharacter = character) }

        when (state.value.layoutMode) {
            LayoutMode.Horizontal -> {
                setBackStack(CharactersEntryDestination.Dashboard)
            }

            LayoutMode.Vertical, null -> {
                setBackStack(
                    CharactersEntryDestination.List,
                    CharactersEntryDestination.Details(character.id)
                )
            }
        }
    }

    fun navigateBack(onNavigateBackFromParent: () -> Unit) {
        val backStack = state.value.backStack

        val canPopInternalEntry = state.value.layoutMode == LayoutMode.Vertical && backStack.size > 1
        if (canPopInternalEntry) {
            backStack.removeLastOrNull()

            if (backStack.lastOrNull() == CharactersEntryDestination.List) {
                _state.update { it.copy(selectedCharacter = null) }
            }
            return
        }

        onNavigateBackFromParent()
    }

    private fun setBackStack(vararg destinations: CharactersEntryDestination) {
        val backStack = state.value.backStack
        backStack.clear()
        backStack.addAll(destinations)
    }
}
