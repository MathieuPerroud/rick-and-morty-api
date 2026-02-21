package dev.xnative.cleanrmapi.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * Common contract for view models exposing reactive state and one-shot events.
 */
interface StateAwareViewModel<State> {
    val state: StateFlow<State>
    val events: Flow<Any>
}

/**
 * Generic ViewModel base used by screens that do not rely on [Store].
 */
open class ViewModel<State>(initialState: State) :
    androidx.lifecycle.ViewModel(),
    StateAwareViewModel<State>,
    KoinComponent {

    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<State>
        get() = _state

    private val _events = Channel<Any>(Channel.Factory.BUFFERED)
    override val events: Flow<Any>
        get() = _events.receiveAsFlow()

    /**
     * Applies a pure transformation on the current state.
     */
    protected fun ViewModel<State>.updateState(block: State.() -> State) {
        _state.update { block.invoke(it) }
    }

    /**
     * Emits a one-shot event to observers.
     */
    protected fun ViewModel<State>.sendEvent(obj: Any) {
        viewModelScope.launch {
            _events.send(obj)
        }
    }

    /**
     * Collects a flow source in background and reports updates on Main dispatcher.
     */
    fun <T> androidx.lifecycle.ViewModel.collectData(
        source: suspend () -> Flow<T>,
        onResult: Result<T>.() -> Unit
    ) {
        viewModelScope.launch(Dispatchers.Default) {

            try {
                source().collect { newValue ->
                    launch(Dispatchers.Main) { onResult(Result.success(newValue)) }
                }

            } catch (ex: Throwable) {
                launch(Dispatchers.Main) { onResult(Result.failure(ex)) }
            }

        }

    }

    /**
     * Executes a one-shot suspend source in background and reports on Main dispatcher.
     */
    fun <T> androidx.lifecycle.ViewModel.fetchData(
        source: suspend () -> T,
        onResult: Result<T>.() -> Unit
    ) {
        viewModelScope.launch(Dispatchers.Default) {

            try {
                val success = source()
                launch(Dispatchers.Main) { onResult(Result.success(success)) }
            } catch (ex: Throwable) {
                launch(Dispatchers.Main) { onResult(Result.failure(ex)) }
            }

        }

    }

}

/**
 * ViewModel adapter around a [Store] instance.
 *
 * It bridges store state/events to Compose and forwards [StoreAction] execution.
 */
open class StoreViewModel<State, StateStore : Store<State>, Action : StoreAction<State, StateStore>>(
    protected val store: StateStore
) :
    androidx.lifecycle.ViewModel(),
    KoinComponent,
    StateAwareViewModel<State> {

    init {
        this.addCloseable(store.storeScope)
    }

    override val state: StateFlow<State>
        get() = store.state
    override val events: Flow<Any>
        get() = store.events

    fun handleAction(action: Action): Unit = action.execute(from = store)

}

/**
 * Reducer contract executed against a typed [Store].
 */
interface StoreAction<State, StateStore : Store<State>> : KoinComponent {
    fun execute(from: StateStore) = with(this) {
        from.reduce()
    }

    fun StateStore.reduce()
}
