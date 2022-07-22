package dev.sergiobelda.foundry.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

interface Destination {
    val route: String
}

data class TopLevelDestination(
    override val route: String
) : Destination

class Action(private val navController: NavHostController) {
    fun navigateUp() {
        navController.navigateUp()
    }

    fun navigate(destination: Destination) {
        if (destination is TopLevelDestination) {
            navController.navigate(destination.route) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        } else {
            navController.navigate(destination.route)
        }
    }
}
