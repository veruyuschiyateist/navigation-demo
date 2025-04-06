package com.plko.bls.app

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.plko.bls.app.ui.screens.AppTab
import com.plko.bls.app.ui.screens.routeClass
import kotlinx.collections.immutable.ImmutableList

@Composable
fun AppNavigationBar(
    navController: NavController,
    tabs: ImmutableList<AppTab>
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val closestNavGraphDestination = currentBackStackEntry?.destination?.hierarchy?.first {
            it is NavGraph
        }
        val closestNavGraphClass = closestNavGraphDestination.routeClass()
        val currentTab = tabs.firstOrNull { it.graph::class == closestNavGraphClass }

        tabs.forEach { tab ->
            NavigationBarItem(
                selected = currentTab == tab,
                onClick = {
                    if (currentTab != null) {
                        navController.navigate(tab.graph) {
                            popUpTo(currentTab.graph) {
                                inclusive = true
                                saveState = true
                            }
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(imageVector = tab.icon, contentDescription = null)
                },
                label = {
                    Text(
                        text = stringResource(id = tab.labelRes)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.tertiaryContainer,
                    selectedIconColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            )
        }
    }
}