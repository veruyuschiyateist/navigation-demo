package com.plko.bls.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.plko.bls.app.ui.components.AppToolbar
import com.plko.bls.app.ui.components.NavigateUpAction
import com.plko.bls.app.ui.screens.AddItemRoute
import com.plko.bls.app.ui.screens.EditItemRoute
import com.plko.bls.app.ui.screens.ItemsRoute
import com.plko.bls.app.ui.screens.LocalNavController
import com.plko.bls.app.ui.screens.add.AddItemScreen
import com.plko.bls.app.ui.screens.edit.EditItemScreen
import com.plko.bls.app.ui.screens.items.ItemsScreen
import com.plko.bls.app.ui.screens.routeClass
import com.plko.bls.app.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigInteger

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                NavApp()
            }
        }
    }
}

@Composable
fun NavApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val titleRes = when (currentBackStackEntry?.routeClass()) {
        ItemsRoute::class -> R.string.items
        AddItemRoute::class -> R.string.add_item
        EditItemRoute::class -> R.string.edit_item
        else -> R.string.app_name
    }

    Scaffold(
        topBar = {
            AppToolbar(
                titleRes = titleRes,
                navigateUpAction = if (navController.previousBackStackEntry == null) {
                    NavigateUpAction.Hidden
                } else {
                    NavigateUpAction.Visible(
                        onClick = {
                            navController.navigateUp()
                        }
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AddItemRoute)
                },
                modifier = Modifier
                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        CompositionLocalProvider(
            LocalNavController provides navController
        ) {
            NavHost(
                navController = navController,
                startDestination = ItemsRoute,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                composable<ItemsRoute>() { ItemsScreen() }
                composable<AddItemRoute>() { AddItemScreen() }
                composable<EditItemRoute>() { entry ->
                    val route: EditItemRoute = entry.toRoute()
                    EditItemScreen(index = route.index)
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        NavApp()
    }
}