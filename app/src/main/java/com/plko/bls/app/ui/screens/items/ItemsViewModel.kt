package com.plko.bls.app.ui.screens.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plko.bls.app.model.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository
) : ViewModel() {

    val stateFlow = itemsRepository.getItems()
        .map(ScreenState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ScreenState.Loading
        )

    sealed interface ScreenState {
        data object Loading : ScreenState
        data class Success(val items: List<String>) : ScreenState
    }
}