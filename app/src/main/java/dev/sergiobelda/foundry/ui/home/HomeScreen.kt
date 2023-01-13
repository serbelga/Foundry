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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.TextFields
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import dev.sergiobelda.foundry.R
import dev.sergiobelda.foundry.ui.components.FontListView
import dev.sergiobelda.foundry.ui.resources.FAB_VISIBLE_ITEM_INDEX
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
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
    val fontsListState = rememberLazyListState()
    val favoritesListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var searchText by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    fun closeSearchBar() {
        focusManager.clearFocus()
        active = false
        searchText = ""
    }
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                LazyColumn {
                    items(navigationItems) {
                        NavigationDrawerItem(
                            icon = {
                                Image(
                                    imageVector = it.imageVector,
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = stringResource(id = it.stringResourceId)) },
                            selected = currentNavigationItem == it,
                            onClick = {
                                currentNavigationItem = it
                                coroutineScope.launch { drawerState.close() }
                            }
                        )
                    }
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                /*
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
                */
                SearchBar(
                    query = searchText,
                    onQueryChange = { searchText = it },
                    onSearch = { closeSearchBar() },
                    active = active,
                    onActiveChange = {
                        active = it
                        if (!active) focusManager.clearFocus()
                    },
                    placeholder = { Text(stringResource(id = R.string.search_fonts)) },
                    leadingIcon = {
                        if (active) {
                            IconButton(onClick = { closeSearchBar() }) {
                                Icon(Icons.Rounded.ArrowBack, contentDescription = null)
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    coroutineScope.launch { drawerState.open() }
                                    closeSearchBar()
                                }
                            ) {
                                Icon(Icons.Rounded.Menu, contentDescription = null)
                            }
                        }
                    },
                    trailingIcon = {
                        if (active) {
                            IconButton(onClick = { searchText = "" }) {
                                Icon(Icons.Rounded.Clear, contentDescription = null)
                            }
                        } else {
                            IconButton(onClick = { closeSearchBar() }) {
                                Icon(Icons.Rounded.MoreVert, contentDescription = null)
                            }
                        }
                    },
                    modifier = Modifier.padding(0.dp)
                ) {
                    Row {
                        FilterChip(
                            selected = false,
                            onClick = {},
                            label = { Text("Filter chip") },
                            leadingIcon = {}
                        )
                        FilterChip(
                            selected = false,
                            onClick = {},
                            label = { Text("Filter chip") },
                            leadingIcon = {}
                        )
                    }
                }
            },
            bottomBar = {
                /*
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
                */
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
}
