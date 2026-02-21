package dev.xnative.cleanrmapi.characters.presentation.components.usecases

import dev.xnative.cleanrmapi.characters.domain.CharacterRepository
import dev.xnative.cleanrmapi.characters.presentation.components.ListOfCharactersComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Centralizes the "load more" workflow for [ListOfCharactersComponent.Loaded].
 *
 * The caller provides:
 * - how to read the current loaded state,
 * - how to update that loaded state in its own store,
 * - how to execute async work (Store.fetchData wrapper).
 *
 * This allows multiple screens/stores to share the same pagination behavior.
 */
class LoadMoreCharactersUseCase : KoinComponent {

    private val characterRepository: CharacterRepository by inject()

    fun execute(
        getLoadedState: () -> ListOfCharactersComponent.Loaded?,
        setLoadedState: (ListOfCharactersComponent.Loaded) -> Unit,
        executeFetchData: (
            source: suspend () -> Unit,
            onResult: Result<Unit>.() -> Unit
        ) -> Unit
    ) {
        val currentState = getLoadedState() ?: return
        if (currentState.isLoadingMore) return

        setLoadedState(
            currentState.copy(
                isLoadingMore = true
            )
        )

        executeFetchData(characterRepository::loadMore) {
            onSuccess {
                val loadedState = getLoadedState() ?: return@executeFetchData

                setLoadedState(
                    loadedState.copy(
                        isLoadingMore = false,
                        loadingMoreError = null
                    )
                )
            }

            onFailure { throwable ->
                val loadedState = getLoadedState() ?: return@executeFetchData

                setLoadedState(
                    loadedState.copy(
                        isLoadingMore = false,
                        loadingMoreError = throwable.toString()
                    )
                )
            }
        }
    }
}
