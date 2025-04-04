package com.plko.bls.app.ui.screens.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plko.bls.app.R
import com.plko.bls.app.ui.components.ItemDetails
import com.plko.bls.app.ui.components.ItemDetailsState
import com.plko.bls.app.ui.screens.AddItemRoute
import com.plko.bls.app.ui.screens.EventConsumer
import com.plko.bls.app.ui.screens.LocalNavController
import com.plko.bls.app.ui.screens.action.ActionScreen
import com.plko.bls.app.ui.screens.routeClass

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