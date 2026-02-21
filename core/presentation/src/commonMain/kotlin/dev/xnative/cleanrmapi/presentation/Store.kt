package dev.xnative.cleanrmapi.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Base reactive state container used by screen ViewModels.
 *
 * Design choices:
 * - Owns a dedicated [storeScope] so side effects are isolated from UI composition.
 * - Uses [SupervisorJob] to prevent one failing child coroutine from cancelling siblings.
 * - Exposes state via [StateFlow] and one-shot events via a buffered [Channel].
 *
 * [storeScope] is [AutoCloseable], so a hosting ViewModel can close it with lifecycle APIs.
 */
abstract class Store<State>(
    initialState: State,
    val storeScope: CloseableCoroutineScope = CloseableCoroutineScope(
        SupervisorJob() + Dispatchers.Main.immediate
    )
) {

    private val _events = Channel<Any>(Channel.BUFFERED)
    val events: Flow<Any>
        get() = _events.receiveAsFlow()

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State>
        get() = _state

    /**
     * Applies a pure state transformation on the current state.
     */
    fun Store<State>.updateState(block: State.() -> State) {
        _state.update { block.invoke(it) }
    }

    /**
     * Emits a one-shot event to observers.
     */
    fun Store<State>.sendEvent(obj: Any) {
        storeScope.launch {
            _events.send(obj)
        }
    }

    /**
     * Collects a flow source in background and forwards each result on Main.
     *
     * This helper centralizes error forwarding and threading decisions for stores.
     */
    fun <T> Store<State>.collectData(
        source: suspend () -> Flow<T>,
        onResult: Result<T>.() -> Unit
    ) {
        storeScope.launch(Dispatchers.Default) {

            try {
                source().collect { newValue ->
                    launch(Dispatchers.Main) { onResult(Result.success(newValue)) }
                }

            } catch (ex: Throwable) {
                launch(Dispatchers.Main) {
                    sendEvent(ex)
                    onResult(Result.failure(ex))
                }
            }

        }

    }

    /**
     * Executes a one-shot suspend source in background and publishes its result on Main.
     */
    fun <T> Store<State>.fetchData(
        source: suspend () -> T,
        onResult: Result<T>.() -> Unit
    ) {
        storeScope.launch(Dispatchers.Default) {

            try {
                val success = source()
                launch(Dispatchers.Main) { onResult(Result.success(success)) }
            } catch (ex: Throwable) {
                launch(Dispatchers.Main) {
                    sendEvent(ex)
                    onResult(Result.failure(ex))
                }
            }

        }

    }

}

/**
 * Lifecycle-bound [CoroutineScope] that can be closed from hosting components.
 */
class CloseableCoroutineScope(override val coroutineContext: CoroutineContext) : AutoCloseable,
    CoroutineScope {
    override fun close() = coroutineContext.cancel()
}
