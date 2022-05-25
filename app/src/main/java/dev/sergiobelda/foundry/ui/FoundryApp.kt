package dev.sergiobelda.foundry.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.sergiobelda.foundry.ui.main.MainScreen
import dev.sergiobelda.foundry.ui.theme.FoundryTheme

@Composable
fun FoundryApp() {
    MainScreen()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FoundryTheme {
        FoundryApp()
    }
}
