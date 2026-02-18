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

open class ViewModel<State>(initialState: State): androidx.lifecycle.ViewModel(),
    KoinComponent {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State>
        get() = _state

    private val _events = Channel<Any>(Channel.Factory.BUFFERED)
    val events: Flow<Any>
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