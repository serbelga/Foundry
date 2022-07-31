/*
 * Copyright 2022 Sergio Belda Galbis
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

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.TextFields
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.googlefonts.GoogleFont
import dev.sergiobelda.foundry.R
import dev.sergiobelda.foundry.ui.components.FontListView
import dev.sergiobelda.foundry.ui.resources.FAB_VISIBLE_ITEM_INDEX
import dev.sergiobelda.foundry.ui.theme.pacificoFontFamily
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

sealed class NavigationItem(
    val imageVector: ImageVector,
    @StringRes val stringResourceId: Int
) {
    object FontsNavigationItem :
        NavigationItem(
            Icons.Rounded.TextFields,
            R.string.fonts
        )

    object FavoritesNavigationItem :
        NavigationItem(
            Icons.Rounded.Favorite,
            R.string.favorites
        )
}

val navigationItems =
    listOf(NavigationItem.FontsNavigationItem, NavigationItem.FavoritesNavigationItem)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    fontsViewModel: HomeViewModel = getViewModel()
) {
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    var currentNavigationItem: NavigationItem by remember { mutableStateOf(NavigationItem.FontsNavigationItem) }
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior(topAppBarState) }
    val fontsListState = rememberLazyListState()
    val favoritesListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            fontFamily = pacificoFontFamily,
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    scrollBehavior = scrollBehavior
                )
                if (fontsViewModel.homeUiState.isFetchingFonts) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
        },
        bottomBar = {
            NavigationBar {
                navigationItems.forEach { navigationItem ->
                    NavigationBarItem(
                        selected = currentNavigationItem == navigationItem,
                        onClick = { currentNavigationItem = navigationItem },
                        icon = { Icon(navigationItem.imageVector, contentDescription = null) },
                        label = {
                            Text(
                                text = stringResource(id = navigationItem.stringResourceId)
                            )
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = if (currentNavigationItem == NavigationItem.FontsNavigationItem) {
                    fontsListState
                } else {
                    favoritesListState
                }.firstVisibleItemIndex >= FAB_VISIBLE_ITEM_INDEX,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            if (currentNavigationItem == NavigationItem.FontsNavigationItem) {
                                fontsListState
                            } else {
                                favoritesListState
                            }.animateScrollToItem(0)
                        }
                    }
                ) {
                    Icon(Icons.Rounded.ArrowUpward, contentDescription = null)
                }
            }
        }
    ) { paddingValues ->
        when (currentNavigationItem) {
            is NavigationItem.FontsNavigationItem -> {
                FontListView(
                    fontsListState,
                    fontsViewModel.homeUiState.fontItems,
                    provider,
                    onFavoriteClick = { fontsViewModel.updateFontFavoriteState(it) },
                    modifier = Modifier.padding(paddingValues)
                )
            }
            is NavigationItem.FavoritesNavigationItem -> {
                FontListView(
                    favoritesListState,
                    fontsViewModel.homeUiState.favoriteFontItems,
                    provider,
                    onFavoriteClick = { fontsViewModel.updateFontFavoriteState(it) },
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}
