package dev.sergiobelda.foundry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.sergiobelda.foundry.ui.FoundryApp
import dev.sergiobelda.foundry.ui.theme.FoundryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoundryTheme {
                FoundryApp()
            }
        }
    }
}
