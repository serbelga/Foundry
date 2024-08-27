/*
 * Copyright 2022 Sergio Belda
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

package dev.sergiobelda.foundry.ui.home

import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.sergiobelda.foundry.ui.home.content.HomeFontsContent
import dev.sergiobelda.foundry.ui.home.content.HomeSavedFontsContent
import dev.sergiobelda.foundry.ui.home.menu.HomeMenuContent
import dev.sergiobelda.foundry.ui.home.menu.HomeMenuNavigationItem
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinInject()
) {
    var selectedHomeMenuNavigationItem: HomeMenuNavigationItem by remember {
        mutableStateOf(
            HomeMenuNavigationItem.FontsMenuNavigationItem,
        )
    }
    val homeUiState = rememberHomeUiState()

    ModalNavigationDrawer(
        drawerContent = {
            HomeMenuContent(
                homeMenuNavigationItemSelected = selectedHomeMenuNavigationItem,
                onHomeMenuNavigationItemClick = {
                    when (it) {
                        HomeMenuNavigationItem.FontsMenuNavigationItem -> {

                        }
                        HomeMenuNavigationItem.SavedFontsMenuNavigationItem -> {

                        }
                        HomeMenuNavigationItem.SettingsMenuNavigationItem -> {
                            // TODO: Navigate to Settings
                        }
                    }
                    if (it.isSelectable) {
                        selectedHomeMenuNavigationItem = it
                    }
                    homeUiState.closeDrawer()
                },
            )
        },
        drawerState = homeUiState.drawerState,
    ) {
        when (selectedHomeMenuNavigationItem) {
            HomeMenuNavigationItem.FontsMenuNavigationItem -> {
                HomeFontsContent(
                    fonts = viewModel.state.fontItems,
                    onOpenHomeDrawerClick = { homeUiState.openDrawer() },
                    updateFontSavedState = viewModel::updateFontSavedState,
                )
            }
            HomeMenuNavigationItem.SavedFontsMenuNavigationItem -> {
                HomeSavedFontsContent(
                    fonts = viewModel.state.savedFontItems,
                    onOpenHomeDrawerClick = { homeUiState.openDrawer() },
                    updateFontSavedState = viewModel::updateFontSavedState,
                )
            }
            else -> Unit
        }
    }
}
