/*
 * Copyright 2024 Sergio Belda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.sergiobelda.foundry.presentation.ui.home.menu

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.TextFields
import androidx.compose.ui.graphics.vector.ImageVector
import dev.sergiobelda.foundry.presentation.R

internal enum class HomeMenuNavigationItem(
    val imageVector: ImageVector,
    @StringRes val stringResourceId: Int,
    val isSelectable: Boolean = true
) {
    FontsMenuNavigationItem(Icons.Rounded.TextFields, R.string.fonts),
    SavedFontsMenuNavigationItem(Icons.Rounded.Bookmarks, R.string.saved_fonts),
    SettingsMenuNavigationItem(Icons.Outlined.Settings, R.string.settings, isSelectable = false),
}
