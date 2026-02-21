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
 * @param storeScope [CoroutineScope] tied to this [Store].
 * As this scope implements [kotlin.AutoCloseable], it can be canceled with other closeables for example
 * in onDestroys functions. i.e [androidx.lifecycle.ViewModel.clear]
 *
 * SupervisorJob ensure that each Job can be independent. If one fail it only stops itself.
 *
 * This scope is bound to
 * [Dispatchers.Main.immediate][kotlinx.coroutines.MainCoroutineDispatcher.immediate]
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


    /** This function is made as an extension because when we call it, it is colored
     * and this is better for our DeveloperX. */
    fun Store<State>.updateState(block: State.() -> State) {
        _state.update { block.invoke(it) }
    }

    /** This function is made as an extension because when we call it, it is colored
     * and this is better for our DeveloperX. */
    fun Store<State>.sendEvent(obj: Any) {
        storeScope.launch {
            _events.send(obj)
        }
    }

    /** This function is made as an extension because when we call it, it is yellow
     * and this is better for our UX too*/
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

class CloseableCoroutineScope(override val coroutineContext: CoroutineContext) : AutoCloseable,
    CoroutineScope {
    override fun close() = coroutineContext.cancel()
}