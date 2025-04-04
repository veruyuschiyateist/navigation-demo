package com.plko.bls.app.ui.screens.edit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.plko.bls.app.R
import com.plko.bls.app.ui.components.ItemDetails
import com.plko.bls.app.ui.components.ItemDetailsState
import com.plko.bls.app.ui.screens.action.ActionScreen
import com.plko.bls.app.ui.screens.edit.EditItemViewModel.ScreenState

@Composable
fun EditItemScreen(index: Int) {
    val viewModel = hiltViewModel<EditItemViewModel, EditItemViewModel.Factory> { factory ->
        factory.create(index)
    }

    ActionScreen(
        delegate = viewModel, content = { (screenState, onExecuteAction) ->
            EditItemContent(screenState, onExecuteAction)
        })
}

@Composable
fun EditItemContent(
    state: ScreenState, onEditButtonClicked: (String) -> Unit
) {
    ItemDetails(
        state = ItemDetailsState(
            loadedItem = state.loadedItem,
            textFieldPlaceholder = stringResource(R.string.edit_item),
            actionButtonText = stringResource(R.string.edit_item),
            isActionInProgress = state.isEditInProgress
        ), onActionButtonClicked = onEditButtonClicked, modifier = Modifier.fillMaxSize()
    )
}