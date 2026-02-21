package dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.actions

import dev.xnative.cleanrmapi.characters.domain.CharacterRepository
import dev.xnative.cleanrmapi.characters.presentation.components.CharacterDetailsComponent
import dev.xnative.cleanrmapi.characters.presentation.extensions.toLoadedCharacterState
import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.CharactersDashboardContracts
import dev.xnative.cleanrmapi.characters.presentation.screens.charactersdashboard.CharactersDashboardStore
import dev.xnative.cleanrmapi.domain.character.models.CharacterPreview
import org.koin.core.component.inject

/**
 * Loads and exposes details for the selected character in dashboard mode.
 */
class ShowCharacterDetails(
    val character: CharacterPreview
) : CharactersDashboardContracts.UiAction {

    private val characterRepository: CharacterRepository by inject()

    override fun CharactersDashboardStore.reduce() {
        updateState {
            copy(
                detailSection = CharactersDashboardContracts.CharacterSelected(
                    characterDetailsState = CharacterDetailsComponent.Loading
                )
            )
        }

        fetchData(
            source = { characterRepository.getCharacterDetailedLocalFirst(character.id) }
        ) {
            onSuccess { details ->
                updateState {
                    copy(
                        detailSection = CharactersDashboardContracts.CharacterSelected(
                            characterDetailsState = details.toLoadedCharacterState()
                        )
                    )
                }
            }

            onFailure {
                updateState {
                    copy(detailSection = CharactersDashboardContracts.NoCharacterSelected)
                }
            }
        }
    }
}
