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
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.TextFields
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.sergiobelda.foundry.R
import dev.sergiobelda.foundry.ui.components.FontListView
import dev.sergiobelda.foundry.ui.resources.FAB_VISIBLE_ITEM_INDEX
import dev.sergiobelda.foundry.ui.theme.pacificoFontFamily
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

enum class HomeMenuNavigationItem(
    val imageVector: ImageVector,
    @StringRes val stringResourceId: Int
) {
    FontsMenuNavigationItem(Icons.Rounded.TextFields, R.string.fonts),
    FavoritesMenuNavigationItem(Icons.Rounded.Favorite, R.string.favorites),
    SettingsMenuNavigationItem(Icons.Outlined.Settings, R.string.settings)
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalTextApi::class, ExperimentalAnimationApi::class,
    ExperimentalAnimationGraphicsApi::class
)
@Composable
fun HomeScreen(
    fontsViewModel: HomeViewModel = getViewModel()
) {
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    var currentHomeMenuNavigationItem: HomeMenuNavigationItem by remember {
        mutableStateOf(
            HomeMenuNavigationItem.FontsMenuNavigationItem
        )
    }
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
    val fontsListState = rememberLazyListState()
    val favoritesListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var searchText by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    val avdMenuToArrowBack =
        AnimatedImageVector.animatedVectorResource(R.drawable.avd_menu_to_arrow_back)
    val avdMenuToArrowBackPainter = rememberAnimatedVectorPainter(avdMenuToArrowBack, active)
    val focusManager = LocalFocusManager.current

    val fabVisible by remember {
        derivedStateOf {
            if (currentHomeMenuNavigationItem == HomeMenuNavigationItem.FontsMenuNavigationItem) {
                fontsListState
            } else {
                favoritesListState
            }.firstVisibleItemIndex >= FAB_VISIBLE_ITEM_INDEX
        }
    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    fun closeSearchBar() {
        focusManager.clearFocus()
        active = false
        searchText = ""
    }
    ModalNavigationDrawer(
        drawerContent = {
            DrawerContent(
                homeMenuNavigationItemSelected = currentHomeMenuNavigationItem,
                onHomeMenuNavigationItemClick = {
                    when (it) {
                        HomeMenuNavigationItem.FontsMenuNavigationItem -> currentHomeMenuNavigationItem =
                            it

                        HomeMenuNavigationItem.FavoritesMenuNavigationItem -> currentHomeMenuNavigationItem =
                            it

                        HomeMenuNavigationItem.SettingsMenuNavigationItem -> {
                            // TODO: Navigate to Settings
                        }
                    }
                    coroutineScope.launch { drawerState.close() }
                }
            )
        },
        drawerState = drawerState
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                val onActiveChange: (Boolean) -> Unit = {
                    active = it
                    if (!active) focusManager.clearFocus()
                }
                SearchBar(
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = searchText,
                            onQueryChange = { searchText = it },
                            onSearch = { closeSearchBar() },
                            expanded = active,
                            onExpandedChange = onActiveChange,
                            enabled = true,
                            placeholder = { Text(stringResource(id = R.string.search_fonts)) },
                            leadingIcon = {
                                if (active) {
                                    IconButton(onClick = { closeSearchBar() }) {
                                        Icon(painter = avdMenuToArrowBackPainter, contentDescription = null)
                                    }
                                } else {
                                    IconButton(
                                        onClick = {
                                            coroutineScope.launch { drawerState.open() }
                                            closeSearchBar()
                                        }
                                    ) {
                                        Icon(painter = avdMenuToArrowBackPainter, contentDescription = null)
                                    }
                                }
                            },
                            trailingIcon = {
                                if (active) {
                                    IconButton(onClick = { searchText = "" }) {
                                        Icon(Icons.Rounded.Clear, contentDescription = null)
                                    }
                                }
                            },
                            interactionSource = null,
                        )
                    },
                    expanded = active,
                    onExpandedChange = onActiveChange,
                    modifier = Modifier
                        .fillMaxWidth(),
                    content = {}
                )
            },
            floatingActionButton = {
                AnimatedVisibility(
                    visible = fabVisible,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    FloatingActionButton(
                        onClick = {
                            coroutineScope.launch {
                                if (currentHomeMenuNavigationItem == HomeMenuNavigationItem.FontsMenuNavigationItem) {
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
            when (currentHomeMenuNavigationItem) {
                HomeMenuNavigationItem.FontsMenuNavigationItem -> {
                    FontListView(
                        fontsListState,
                        fontsViewModel.homeUiState.fontItems,
                        provider,
                        onFavoriteClick = { fontsViewModel.updateFontFavoriteState(it) },
                        modifier = Modifier.padding(paddingValues)
                    )
                }

                HomeMenuNavigationItem.FavoritesMenuNavigationItem -> {
                    FontListView(
                        favoritesListState,
                        fontsViewModel.homeUiState.favoriteFontItems,
                        provider,
                        onFavoriteClick = { fontsViewModel.updateFontFavoriteState(it) },
                        modifier = Modifier.padding(paddingValues)
                    )
                }

                else -> {}
            }
        }
    }
}

@Composable
private fun DrawerContent(
    onHomeMenuNavigationItemClick: (HomeMenuNavigationItem) -> Unit,
    homeMenuNavigationItemSelected: HomeMenuNavigationItem
) {
    ModalDrawerSheet {
        Text(
            text = stringResource(id = R.string.app_name),
            fontFamily = pacificoFontFamily,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp,
            modifier = Modifier.padding(
                start = 24.dp,
                top = 8.dp,
                end = 8.dp,
                bottom = 8.dp
            )
        )
        Divider()
        HomeMenuSpacer()
        HomeMenuNavigationDrawerItem(
            homeMenuNavigationItem = HomeMenuNavigationItem.FontsMenuNavigationItem,
            onClick = { onHomeMenuNavigationItemClick(HomeMenuNavigationItem.FontsMenuNavigationItem) },
            selected = homeMenuNavigationItemSelected == HomeMenuNavigationItem.FontsMenuNavigationItem
        )
        HomeMenuNavigationDrawerItem(
            homeMenuNavigationItem = HomeMenuNavigationItem.FavoritesMenuNavigationItem,
            onClick = { onHomeMenuNavigationItemClick(HomeMenuNavigationItem.FavoritesMenuNavigationItem) },
            selected = homeMenuNavigationItemSelected == HomeMenuNavigationItem.FavoritesMenuNavigationItem
        )
        HomeMenuSpacer()
        Divider(modifier = Modifier.padding(horizontal = HomeMenuDividerHorizontalPadding))
        HomeMenuSpacer()
        HomeMenuNavigationDrawerItem(
            homeMenuNavigationItem = HomeMenuNavigationItem.SettingsMenuNavigationItem,
            onClick = { onHomeMenuNavigationItemClick(HomeMenuNavigationItem.SettingsMenuNavigationItem) }
        )
    }
}

@Composable
private fun HomeMenuSpacer() {
    Spacer(modifier = Modifier.height(HomeMenuSpacerHeight))
}

@Composable
private fun HomeMenuNavigationDrawerItem(
    homeMenuNavigationItem: HomeMenuNavigationItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false
) {
    NavigationDrawerItem(
        icon = {
            Icon(imageVector = homeMenuNavigationItem.imageVector, contentDescription = null)
        },
        label = {
            Text(
                text = stringResource(id = homeMenuNavigationItem.stringResourceId),
                style = MaterialTheme.typography.labelLarge
            )
        },
        selected = selected,
        onClick = onClick,
        modifier = modifier.padding(
            start = HomeMenuNavigationDrawerItemPadding,
            end = HomeMenuNavigationDrawerItemPadding
        )
    )
}

private val HomeMenuDividerHorizontalPadding: Dp = 28.dp
private val HomeMenuSpacerHeight: Dp = 12.dp
private val HomeMenuNavigationDrawerItemPadding: Dp = 8.dp
