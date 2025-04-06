package com.plko.bls.app.ui.screens.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plko.bls.app.model.LoadResult
import com.plko.bls.app.ui.components.LoadResultContent
import com.plko.bls.app.ui.screens.ItemsGraph.EditItemRoute
import com.plko.bls.app.ui.screens.LocalNavController

@Composable
fun ItemsScreen() {
    val viewModel: ItemsViewModel = hiltViewModel<ItemsViewModel>()
    val navController = LocalNavController.current
    val screenState = viewModel.stateFlow.collectAsState()

    ItemsContent(
        getLoadResult = { screenState.value },
        onItemClicked = { idx ->
            navController.navigate(EditItemRoute(idx))
        }
    )
}

@Composable
fun ItemsContent(
    getLoadResult: () -> LoadResult<ItemsViewModel.ScreenState>,
    onItemClicked: (Int) -> Unit
) {

    LoadResultContent(
        loadResult = getLoadResult(),
        content = { screenState ->
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(screenState.items) { idx, item ->
                    Text(
                        text = item,
                        modifier = Modifier
                            .clickable { onItemClicked(idx) }
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                }
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
private fun ItemsPreview() {
    ItemsContent(
        getLoadResult = { LoadResult.Loading },
        onItemClicked = {}
    )
}