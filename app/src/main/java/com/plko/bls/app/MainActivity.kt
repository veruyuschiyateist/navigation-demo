package com.plko.bls.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.plko.bls.app.ui.screens.AddItemRoute
import com.plko.bls.app.ui.screens.ItemsRoute
import com.plko.bls.app.ui.screens.LocalNavController
import com.plko.bls.app.ui.screens.add.AddItemScreen
import com.plko.bls.app.ui.screens.items.ItemsScreen
import com.plko.bls.app.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
    
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
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = ItemsRoute,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(ItemsRoute) { ItemsScreen() }
            composable(AddItemRoute) { AddItemScreen() }
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