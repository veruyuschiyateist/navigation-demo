package com.plko.bls.app.ui.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plko.bls.app.model.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository
) : ViewModel() {

    private val stateMutableFlow = MutableStateFlow(ScreenState())
    val stateFlow: StateFlow<ScreenState> = stateMutableFlow.asStateFlow()

    private val _exitChannel = Channel<Unit>()
    val exitChannel: ReceiveChannel<Unit> = _exitChannel

    fun add(title: String) {
        viewModelScope.launch {
            stateMutableFlow.update { it.copy(isAddInProgress = true) }
            itemsRepository.add(item = title)
            _exitChannel.send(Unit)
        }
    }

    data class ScreenState(
        private val isAddInProgress: Boolean = false
    ) {

        val isTextInputEnabled: Boolean get() = !isAddInProgress
        val isProgressVisible: Boolean get() = isAddInProgress

        fun isAddButtonEnabled(input: String): Boolean {
            return input.isNotEmpty() && !isAddInProgress
        }
    }
}