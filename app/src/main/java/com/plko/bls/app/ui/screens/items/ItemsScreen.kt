package com.plko.bls.app.ui.screens.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plko.bls.app.ui.screens.AddItemRoute
import com.plko.bls.app.ui.screens.LocalNavController

@Composable
fun ItemsScreen() {
    val navController = LocalNavController.current
    ItemsContent(
        onLaunchAddItemScreen = {
            navController.navigate(AddItemRoute)
        }
    )
}

@Composable
fun ItemsContent(
    onLaunchAddItemScreen: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Items screen",
            modifier = Modifier.align(Alignment.Center),
            fontSize = 20.sp
        )

        FloatingActionButton(
            onClick = {
                onLaunchAddItemScreen.invoke()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}