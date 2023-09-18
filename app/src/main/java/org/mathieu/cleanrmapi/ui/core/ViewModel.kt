@file:Suppress("UnusedReceiverParameter")

package org.mathieu.cleanrmapi.ui.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

open class ViewModel<State>(initialState: State, application: Application): AndroidViewModel(application),
    KoinComponent {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State>
        get() = _state

    /** This function is made as an extension because when we call it, it is yellow
     * and this is better for our UX */
    protected fun ViewModel<State>.updateState(block: State.() -> State) {
        _state.update { block.invoke(it) }
    }

    /** This function is made as an extension because when we call it, it is yellow
     * and this is better for our UX too*/
    fun <T> AndroidViewModel.collectData(
        source: suspend () -> Flow<T>,
        onResult: Result<T>.() -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                source().collect { newValue ->
                    launch(Dispatchers.Main) { onResult(Result.success(newValue)) }
                }

            } catch (ex: Throwable) {
                launch(Dispatchers.Main) { onResult(Result.failure(ex)) }
            }

        }

    }

    fun <T> AndroidViewModel.fetchData(
        source: suspend () -> T,
        onResult: Result<T>.() -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val success = source()
                launch(Dispatchers.Main) { onResult(Result.success(success)) }
            } catch (ex: Throwable) {
                launch(Dispatchers.Main) { onResult(Result.failure(ex)) }
            }

        }

    }

}