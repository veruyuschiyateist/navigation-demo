package com.plko.bls.app.ui.screens.edit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.plko.bls.app.R
import com.plko.bls.app.model.LoadResult
import com.plko.bls.app.ui.components.ItemDetails
import com.plko.bls.app.ui.components.ItemDetailsState
import com.plko.bls.app.ui.components.LoadResultContent
import com.plko.bls.app.ui.screens.EditItemRoute
import com.plko.bls.app.ui.screens.EventConsumer
import com.plko.bls.app.ui.screens.LocalNavController
import com.plko.bls.app.ui.screens.edit.EditItemViewModel.ScreenState
import com.plko.bls.app.ui.screens.routeClass

@Composable
fun EditItemScreen(index: Int) {
    val viewModel = hiltViewModel<EditItemViewModel, EditItemViewModel.Factory> { factory ->
        factory.create(index)
    }
    val navController = LocalNavController.current
    EventConsumer(channel = viewModel.exitChannel) {
        if (navController.currentBackStackEntry.routeClass() == EditItemRoute::class) {
            navController.popBackStack()
        }
    }
    val loadResult by viewModel.stateFlow.collectAsState()

    EditItemContent(
        loadResult = loadResult,
        onEditButtonClicked = viewModel::update
    )
}

@Composable
fun EditItemContent(
    loadResult: LoadResult<ScreenState>,
    onEditButtonClicked: (String) -> Unit
) {
    LoadResultContent(
        loadResult = loadResult,
        content = { screenState ->
            SuccessEditItemContent(
                state = screenState,
                onEditButtonClicked = onEditButtonClicked
            )
        }
    )
}

@Composable
fun SuccessEditItemContent(
    state: ScreenState,
    onEditButtonClicked: (String) -> Unit
) {
    ItemDetails(
        state = ItemDetailsState(
            loadedItem = state.loadedItem,
            textFieldPlaceholder = stringResource(R.string.edit_item),
            actionButtonText = stringResource(R.string.edit_item),
            isActionInProgress = state.isEditInProgress
        ),
        onActionButtonClicked = onEditButtonClicked,
        modifier = Modifier.fillMaxSize()
    )
}