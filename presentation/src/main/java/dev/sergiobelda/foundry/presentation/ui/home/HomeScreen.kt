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

package dev.sergiobelda.foundry.presentation.ui.home

import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.sergiobelda.foundry.presentation.ui.home.content.HomeFontsContent
import dev.sergiobelda.foundry.presentation.ui.home.content.HomeSavedFontsContent
import dev.sergiobelda.foundry.presentation.ui.home.menu.HomeMenuContent
import dev.sergiobelda.foundry.presentation.ui.home.menu.HomeMenuNavigationItem
import dev.sergiobelda.foundry.presentation.ui.model.filter.toFilterUiModels
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
    val homeState by viewModel.state.collectAsStateWithLifecycle()

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
                    fonts = homeState.fontItems,
                    filtersUiModel = homeState.filters.toFilterUiModels(),
                    onOpenHomeDrawerClick = { homeUiState.openDrawer() },
                    updateFontSavedState = viewModel::updateFontFamilyLikedState,
                    updateFilters = viewModel::updateFilters,
                )
            }
            HomeMenuNavigationItem.SavedFontsMenuNavigationItem -> {
                HomeSavedFontsContent(
                    fonts = homeState.savedFontItems,
                    onOpenHomeDrawerClick = { homeUiState.openDrawer() },
                    updateFontFamilyLikedState = viewModel::updateFontFamilyLikedState,
                )
            }
            else -> Unit
        }
    }
}
