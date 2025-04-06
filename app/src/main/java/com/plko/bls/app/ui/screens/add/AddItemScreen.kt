package com.plko.bls.app.ui.screens.add

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.plko.bls.app.R
import com.plko.bls.app.ui.components.ItemDetails
import com.plko.bls.app.ui.components.ItemDetailsState
import com.plko.bls.app.ui.screens.action.ActionScreen

@Composable
fun AddItemScreen() {
    val viewModel: AddItemViewModel = hiltViewModel()

    ActionScreen(
        delegate = viewModel,
        content = { (screenState, onExecuteAction) ->
            AddItemContent(screenState, onExecuteAction)
        }
    )
}

@Composable
fun AddItemContent(
    screenState: AddItemViewModel.ScreenState,
    onAddButtonClicked: (String) -> Unit
) {
    ItemDetails(
        state = ItemDetailsState(
            loadedItem = "",
            textFieldPlaceholder = stringResource(R.string.enter_new_item),
            actionButtonText = stringResource(R.string.add),
            isActionInProgress = screenState.isProgressVisible
        ),
        onActionButtonClicked = onAddButtonClicked,
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showSystemUi = true)
@Composable
fun AddItemPreview() {
    AddItemContent(
        screenState = AddItemViewModel.ScreenState(),
        onAddButtonClicked = {}
    )

}