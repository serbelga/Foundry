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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.sergiobelda.foundry.R
import dev.sergiobelda.foundry.domain.model.FilterUpdateData
import dev.sergiobelda.foundry.domain.model.FontFamilyItemModel
import dev.sergiobelda.foundry.ui.home.components.FontFamilyListView
import dev.sergiobelda.foundry.ui.home.search.HomeSearchBar
import dev.sergiobelda.foundry.ui.model.FilterElementChipUiModel
import dev.sergiobelda.foundry.ui.model.FiltersUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeFontsContent(
    fonts: List<FontFamilyItemModel>,
    filters: FiltersUiModel,
    onOpenHomeDrawerClick: () -> Unit,
    updateFontSavedState: (FontFamilyItemModel) -> Unit,
    updateFilters: (FilterUpdateData) -> Unit,
) {
    val homeFontsUiState = rememberHomeFontsUiState()

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
                    filterElementChips = filters.toFilterElementChips {
                        updateFilters(it)
                    },
                    onFiltersClick = {
                        homeFontsUiState.openBottomSheet()
                    },
                )
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = homeFontsUiState.fabVisible,
                enter = scaleIn(),
                exit = scaleOut(),
            ) {
                FloatingActionButton(
                    onClick = {
                        homeFontsUiState.animateScrollToTop()
                    },
                ) {
                    Icon(Icons.Rounded.ArrowUpward, contentDescription = null)
                }
            }
        },
    ) { paddingValues ->
        FontFamilyListView(
            homeFontsUiState.lazyListState,
            fonts,
            onSaveClick = { updateFontSavedState(it) },
            modifier = Modifier
                .padding(paddingValues),
            contentPadding = PaddingValues(top = 4.dp)
        )
        if (homeFontsUiState.openBottomSheet) {
            FiltersModalBottomSheet(
                filters = filters,
                updateFilters = updateFilters,
                onDismissRequest = { homeFontsUiState.closeBottomSheet() },
                sheetState = homeFontsUiState.sheetState
            )
        }
    }
}

// TODO: Remove this suppress
@Suppress("LongMethod")
@Composable
private fun HomeFontsListActionsBar(
    filterElementChips: List<FilterElementChipUiModel>,
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
                colors = if (filterElementChips.isEmpty()) {
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
                items(
                    // TODO: Use key
                    // key = { item -> item },
                    items = filterElementChips
                ) { item  ->
                    FilterInputChip(
                        filterInputElementChip = item,
                        modifier = Modifier.animateItem()
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

@Composable
private fun FilterInputChip(
    filterInputElementChip: FilterElementChipUiModel,
    modifier: Modifier = Modifier
) {
    InputChip(
        selected = filterInputElementChip.isSelected,
        onClick = filterInputElementChip.onClick,
        label = { Text(text = stringResource(filterInputElementChip.labelStringResId)) },
        trailingIcon = { Icon(Icons.Rounded.Clear, contentDescription = null) },
        shape = CircleShape,
        modifier = modifier
    )
}
