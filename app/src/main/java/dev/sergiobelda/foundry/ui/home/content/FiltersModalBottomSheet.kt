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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.sergiobelda.foundry.domain.model.FilterUpdateData
import dev.sergiobelda.foundry.ui.model.FiltersUiModel
import dev.sergiobelda.foundry.ui.model.FontFamilyCategoryFilterChipUiModel
import dev.sergiobelda.foundry.ui.model.FontFamilyCategoryFilterUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FiltersModalBottomSheet(
    filtersUiModel: FiltersUiModel,
    updateFilters: (FilterUpdateData) -> Unit,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier,
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                // TODO: Use key
                // key = { item -> item },
                items = filtersUiModel.filters,
                contentType = { it::class }
            ) { filter  ->
                when (filter) {
                    is FontFamilyCategoryFilterUiModel -> {
                        FontFamilyCategoryFilterSelector(
                            filterUiModel = filter,
                            updateFilters = updateFilters
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FontFamilyCategoryFilterSelector(
    filterUiModel: FontFamilyCategoryFilterUiModel,
    updateFilters: (FilterUpdateData) -> Unit,
) {
    val segmentedButtons = filterUiModel.toFilterChips {
        updateFilters(it)
    }
    Column {
        Text(text = stringResource(filterUiModel.titleStringResId))
        FlowRow {
            segmentedButtons.forEach {
                FontFamilyCategorySegmentedButton(
                    filterChipUiModel = it
                )
            }
        }
    }
}

@Composable
private fun FontFamilyCategorySegmentedButton(
    filterChipUiModel: FontFamilyCategoryFilterChipUiModel,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = filterChipUiModel.isSelected,
        onClick = filterChipUiModel.onClick,
        label = { Text(text = stringResource(filterChipUiModel.labelStringResId)) },
        modifier = modifier
    )
}
