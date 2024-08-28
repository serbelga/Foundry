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

package dev.sergiobelda.foundry.ui.home.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.sergiobelda.foundry.R
import dev.sergiobelda.foundry.domain.model.FontFamilyItemModel
import dev.sergiobelda.foundry.ui.home.components.FontFamilyListView
import dev.sergiobelda.foundry.ui.home.search.HomeSearchBar
import dev.sergiobelda.foundry.ui.resources.FAB_VISIBLE_ITEM_INDEX
import kotlinx.coroutines.launch

@Composable
internal fun HomeFontsContent(
    fonts: List<FontFamilyItemModel>,
    onOpenHomeDrawerClick: () -> Unit,
    updateFontSavedState: (FontFamilyItemModel) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    val fabVisible by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex >= FAB_VISIBLE_ITEM_INDEX
        }
    }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    .padding(horizontal = 12.dp)
            ) {
                HomeSearchBar(
                    onMenuIconButtonClick = onOpenHomeDrawerClick,
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                )
                HomeFontsListActionsBar()
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = fabVisible,
                enter = scaleIn(),
                exit = scaleOut(),
            ) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(0)
                        }
                    },
                ) {
                    Icon(Icons.Rounded.ArrowUpward, contentDescription = null)
                }
            }
        },
    ) { paddingValues ->
        FontFamilyListView(
            lazyListState,
            fonts,
            onSaveClick = { updateFontSavedState(it) },
            modifier = Modifier
                .padding(paddingValues),
            contentPadding = PaddingValues(top = 6.dp)
        )
    }
}

@Composable
private fun HomeFontsListActionsBar(
    appliedFilters: List<String> = emptyList()
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    Icons.Rounded.SwapVert,
                    contentDescription = stringResource(R.string.change_sort_order)
                )
            }
            IconButton(
                onClick = {},
                colors = if (appliedFilters.isNotEmpty()) {
                    IconButtonDefaults.filledTonalIconButtonColors()
                } else {
                    IconButtonDefaults.iconButtonColors()
                }
            ) {
                Icon(
                    Icons.Rounded.Tune,
                    contentDescription = stringResource(R.string.filters)
                )
            }
        }
        IconButton(
            onClick = {}
        ) {
            Icon(
                Icons.Outlined.GridView,
                contentDescription = null
            )
        }
    }
}
