package com.plko.bls.app.ui.screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plko.bls.app.model.ItemsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = EditItemViewModel.Factory::class)
class EditItemViewModel @AssistedInject constructor(
    @Assisted private val index: Int,
    private val itemsRepository: ItemsRepository
) : ViewModel() {

    private val stateMutableFlow = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val stateFlow = stateMutableFlow.asStateFlow()

    private val _exitChannel = Channel<Unit>()
    val exitChannel: ReceiveChannel<Unit> = _exitChannel

    init {
        viewModelScope.launch {
            val loadedItem = itemsRepository.getItem(index)

            stateMutableFlow.value = ScreenState.Success(loadedItem)
        }
    }

    fun update(newValue: String) {
        val currentState = stateMutableFlow.value
        if (currentState !is ScreenState.Success) return

        viewModelScope.launch {
            stateMutableFlow.value = currentState.copy(isEditInProgress = true)
            itemsRepository.update(index, newValue)
            _exitChannel.send(Unit)
        }
    }


    sealed class ScreenState {
        data object Loading : ScreenState()

        data class Success(
            val loadedItem: String,
            val isEditInProgress: Boolean = false
        ) : ScreenState()
    }

    @AssistedFactory
    interface Factory {
        fun create(index: Int): EditItemViewModel
    }
}