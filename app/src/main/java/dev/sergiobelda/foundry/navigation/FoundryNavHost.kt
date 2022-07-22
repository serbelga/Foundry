package dev.sergiobelda.foundry.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.sergiobelda.foundry.ui.favorites.FavoritesScreen
import dev.sergiobelda.foundry.ui.fonts.FontsScreen

@Composable
fun FoundryNavHost(navController: NavHostController, action: Action, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = FontsDestination.route,
        modifier = modifier
    ) {
        composable(FontsDestination.route) {
            FontsScreen()
        }
        composable(FavoritesDestination.route) {
            FavoritesScreen()
        }
    }
}
