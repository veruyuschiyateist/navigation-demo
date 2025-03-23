package com.plko.bls.app.ui.screens.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.plko.bls.app.ui.screens.EditItemRoute
import com.plko.bls.app.ui.screens.EventConsumer
import com.plko.bls.app.ui.screens.LocalNavController
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
    val screenState by viewModel.stateFlow.collectAsState()

    EditItemContent(
        state = screenState,
        onEditButtonClicked = viewModel::update
    )
}

@Composable
fun EditItemContent(
    state: EditItemViewModel.ScreenState,
    onEditButtonClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (state) {
            EditItemViewModel.ScreenState.Loading -> {
                CircularProgressIndicator()
            }

            is EditItemViewModel.ScreenState.Success -> {

            }
        }
    }
}

@Composable
fun SuccessEditItemContent(
    state: EditItemViewModel.ScreenState.Success,
    onEditButtonClicked: (String) -> Unit
) {

}