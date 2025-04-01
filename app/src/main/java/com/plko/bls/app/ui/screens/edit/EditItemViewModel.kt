package com.plko.bls.app.ui.screens.edit

import android.transition.Scene
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plko.bls.app.model.ItemsRepository
import com.plko.bls.app.model.LoadResult
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

    private val stateMutableFlow = MutableStateFlow<LoadResult<ScreenState>>(LoadResult.Loading)
    val stateFlow = stateMutableFlow.asStateFlow()


    private val _exitChannel = Channel<Unit>()
    val exitChannel: ReceiveChannel<Unit> = _exitChannel

    init {
        viewModelScope.launch {
            val loadedItem = itemsRepository.getItem(index)

            stateMutableFlow.value = LoadResult.Success(ScreenState(loadedItem))
        }
    }

    fun update(newValue: String) {
        val loadResult = stateMutableFlow.value
        if (loadResult !is LoadResult.Success) return

        viewModelScope.launch {
            showProgress(loadResult)
            itemsRepository.update(index, newValue)
            goBack()
        }
    }

    private fun showProgress(loadResult: LoadResult.Success<ScreenState>) {
        val currentScreenState = loadResult.data
        val updatedScreenState = currentScreenState.copy(isEditInProgress = true)
        stateMutableFlow.value = LoadResult.Success(updatedScreenState)
    }

    private suspend fun goBack() {
        _exitChannel.send(Unit)
    }

    data class ScreenState(
        val loadedItem: String,
        val isEditInProgress: Boolean = false
    )

    @AssistedFactory
    interface Factory {
        fun create(index: Int): EditItemViewModel
    }
}