package dev.sergiobelda.foundry.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.TextFields
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.sergiobelda.foundry.R
import dev.sergiobelda.foundry.navigation.Action
import dev.sergiobelda.foundry.navigation.Destination
import dev.sergiobelda.foundry.navigation.FavoritesDestination
import dev.sergiobelda.foundry.navigation.FontsDestination
import dev.sergiobelda.foundry.navigation.FoundryNavHost
import dev.sergiobelda.foundry.navigation.TopLevelDestination

sealed class NavigationItem(
    val destination: Destination,
    val imageVector: ImageVector,
    @StringRes val stringResourceId: Int
) {
    object FontsNavigationItem :
        NavigationItem(
            TopLevelDestination(route = FontsDestination.route),
            Icons.Rounded.TextFields,
            R.string.fonts
        )

    object FavoritesNavigationItem :
        NavigationItem(
            TopLevelDestination(route = FavoritesDestination.route),
            Icons.Rounded.Favorite,
            R.string.favorites
        )
}

val navigationItems =
    listOf(NavigationItem.FontsNavigationItem, NavigationItem.FavoritesNavigationItem)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, action: Action) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                navigationItems.forEach { navigationItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == navigationItem.destination.route } == true,
                        onClick = {
                            action.navigate(navigationItem.destination)
                        },
                        icon = { Icon(navigationItem.imageVector, contentDescription = null) },
                        label = {
                            Text(
                                text = stringResource(id = navigationItem.stringResourceId)
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        FoundryNavHost(
            navController = navController,
            action = action,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
