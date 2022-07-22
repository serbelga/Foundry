package dev.sergiobelda.foundry.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dev.sergiobelda.foundry.navigation.Action
import dev.sergiobelda.foundry.ui.main.MainScreen
import dev.sergiobelda.foundry.ui.theme.FoundryTheme

@Composable
fun FoundryApp() {
    val navController = rememberNavController()
    val action = remember(navController) { Action(navController) }
    MainScreen(navController, action)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FoundryTheme {
        FoundryApp()
    }
}
