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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.sergiobelda.foundry.R
import dev.sergiobelda.foundry.domain.model.FontFamilyCategoryFilterElementUpdateData
import dev.sergiobelda.foundry.domain.model.FontFamilyItemModel
import dev.sergiobelda.foundry.ui.home.components.FontFamilyListView
import dev.sergiobelda.foundry.ui.home.search.HomeSearchBar
import dev.sergiobelda.foundry.ui.model.FilterElementChip
import dev.sergiobelda.foundry.ui.model.FontFamilyCategoryFilterElementChip
import dev.sergiobelda.foundry.ui.resources.FAB_VISIBLE_ITEM_INDEX
import kotlinx.coroutines.launch

@Composable
internal fun HomeFontsContent(
    fonts: List<FontFamilyItemModel>,
    filters: List<FilterElementChip>,
    onOpenHomeDrawerClick: () -> Unit,
    updateFontSavedState: (FontFamilyItemModel) -> Unit,
    onFiltersClick: () -> Unit,
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
                HomeFontsListActionsBar(
                    filters = filters,
                    onFiltersClick = onFiltersClick,
                )
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
            contentPadding = PaddingValues(top = 4.dp)
        )
    }
}

// TODO: Remove this suppress
@Suppress("LongMethod")
@Composable
private fun HomeFontsListActionsBar(
    filters: List<FilterElementChip>,
    onFiltersClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(bottom = 2.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            IconButton(
                onClick = onFiltersClick,
                colors = if (filters.isEmpty()) {
                    IconButtonDefaults.iconButtonColors()
                } else {
                    IconButtonDefaults.filledTonalIconButtonColors()
                }
            ) {
                Icon(
                    Icons.Rounded.Tune,
                    contentDescription = stringResource(R.string.filters)
                )
            }
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                itemsIndexed(
                    key = { _, item -> item.label },
                    items = filters
                ) { _, item  ->
                    InputChip(
                        selected = item.isSelected,
                        onClick = {
                            when (item) {
                                is FontFamilyCategoryFilterElementChip -> {
                                    item.onClick(
                                        FontFamilyCategoryFilterElementUpdateData(
                                            item.label,
                                            !item.isSelected
                                        )
                                    )
                                }
                            }
                        },
                        label = { Text(text = item.label) },
                        trailingIcon = { Icon(Icons.Rounded.Clear, contentDescription = null) },
                        shape = CircleShape,
                        modifier = Modifier
                            .animateItem()
                    )
                }
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
