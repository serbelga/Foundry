package dev.sergiobelda.foundry.ui.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.TextFields
import androidx.compose.ui.graphics.vector.ImageVector
import dev.sergiobelda.foundry.R

internal enum class HomeMenuNavigationItem(
    val imageVector: ImageVector,
    @StringRes val stringResourceId: Int,
) {
    FontsMenuNavigationItem(Icons.Rounded.TextFields, R.string.fonts),
    SavedFontsMenuNavigationItem(Icons.Rounded.Favorite, R.string.saved_fonts),
    SettingsMenuNavigationItem(Icons.Outlined.Settings, R.string.settings),
}
