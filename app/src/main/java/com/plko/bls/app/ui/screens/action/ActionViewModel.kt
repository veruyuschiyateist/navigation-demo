package com.plko.bls.app.ui.screens.action

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plko.bls.app.model.LoadResult
import com.plko.bls.app.model.tryUpdate
import com.plko.bls.app.ui.screens.edit.EditItemViewModel.ScreenState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ActionViewModel<State, Action>(
    private val delegate: Delegate<State, Action>
) : ViewModel() {

    private val stateMutableFlow = MutableStateFlow<LoadResult<State>>(LoadResult.Loading)
    val stateFlow = stateMutableFlow.asStateFlow()

    private val _exitChannel = Channel<Unit>()
    val exitChannel: ReceiveChannel<Unit> = _exitChannel

    private val _errorChannel = Channel<Exception>()
    val errorChannel: ReceiveChannel<Exception> = _errorChannel

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            stateMutableFlow.value = LoadResult.Loading
            stateMutableFlow.value = try {
                LoadResult.Success(delegate.loadState())
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    fun execute(action: Action) {
        viewModelScope.launch {
            showProgress()
            try {
                delegate.execute(action)
            } catch (e: Exception) {
                hideProgress()
                _errorChannel.send(e)
            }
            goBack()
        }
    }

    private fun showProgress() {
        stateMutableFlow.tryUpdate(delegate::showProgress)
    }

    private fun hideProgress() {
        stateMutableFlow.tryUpdate(delegate::hideProgress)
    }

    private suspend fun goBack() {
        _exitChannel.send(Unit)
    }

    interface Delegate<State, Action> {
        suspend fun loadState(): State

        fun showProgress(input: State): State

        suspend fun execute(action: Action)

        fun hideProgress(input: State): State
    }
}