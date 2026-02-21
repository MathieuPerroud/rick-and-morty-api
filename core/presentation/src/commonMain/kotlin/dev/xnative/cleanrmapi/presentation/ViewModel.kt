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


interface StateAwareViewModel<State> {
    val state: StateFlow<State>
    val events: Flow<Any>
}


open class ViewModel<State>(initialState: State): androidx.lifecycle.ViewModel(), StateAwareViewModel<State>,
    KoinComponent {

    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<State>
        get() = _state

    private val _events = Channel<Any>(Channel.Factory.BUFFERED)
    override val events: Flow<Any>
        get() = _events.receiveAsFlow()

    /** This function is made as an extension because when we call it, it is yellow
     * and this is better for our UX */
    protected fun ViewModel<State>.updateState(block: State.() -> State) {
        _state.update { block.invoke(it) }
    }

    /** This function is made as an extension because when we call it, it is yellow
     * and this is better for our UX */
    protected fun ViewModel<State>.sendEvent(obj: Any) {
        viewModelScope.launch {
            _events.send(obj)
        }
    }

    /** This function is made as an extension because when we call it, it is yellow
     * and this is better for our UX too*/
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

interface StoreAction<State, StateStore : Store<State>> : KoinComponent {
    fun execute(from: StateStore) = with(this) {
        from.reduce()
    }

    fun StateStore.reduce()
}