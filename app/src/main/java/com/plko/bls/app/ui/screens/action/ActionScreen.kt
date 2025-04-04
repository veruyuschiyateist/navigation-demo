package com.plko.bls.app.ui.screens.action

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plko.bls.app.ui.screens.EventConsumer
import androidx.compose.runtime.getValue
import com.plko.bls.app.ui.components.LoadResultContent
import com.plko.bls.app.ui.screens.LocalNavController
import com.plko.bls.app.ui.screens.routeClass

data class ActionContentState<State, Action>(
    val state: State,
    val onExecuteAction: (Action) -> Unit
)

@Composable
fun <State, Action> ActionScreen(
    delegate: ActionViewModel.Delegate<State, Action>,
    content: @Composable (ActionContentState<State, Action>) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel<ActionViewModel<State, Action>> {
        ActionViewModel(delegate)
    }

    val navController = LocalNavController.current

    val rememberedScreeRoute = remember {
        navController.currentBackStackEntry.routeClass()
    }

    EventConsumer(channel = viewModel.exitChannel) {
        if (rememberedScreeRoute == navController.currentBackStackEntry.routeClass()) {
            navController.popBackStack()
        }
    }

    val loadResult by viewModel.stateFlow.collectAsState()

    LoadResultContent(loadResult = loadResult, content = { state ->
        val actionContentState = ActionContentState(
            state = state,
            onExecuteAction = viewModel::execute
        )
        content(actionContentState)
    })

}