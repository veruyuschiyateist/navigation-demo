package com.plko.bls.app.ui.screens.edit

import android.transition.Scene
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plko.bls.app.model.ItemsRepository
import com.plko.bls.app.model.LoadResult
import com.plko.bls.app.ui.screens.action.ActionViewModel
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
) : ViewModel(), ActionViewModel.Delegate<EditItemViewModel.ScreenState, String> {

    override suspend fun loadState(): ScreenState {
        return ScreenState(loadedItem = itemsRepository.getItem(index))
    }

    override fun showProgress(input: ScreenState): ScreenState {
        return input.copy(isEditInProgress = true)
    }

    override suspend fun execute(action: String) {
        itemsRepository.update(index, action)
    }

    override fun hideProgress(input: ScreenState): ScreenState {
        return input.copy(isEditInProgress = false)
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