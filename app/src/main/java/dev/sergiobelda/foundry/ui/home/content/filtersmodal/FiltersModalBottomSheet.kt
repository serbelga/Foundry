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

package dev.sergiobelda.foundry.ui.home.content.filtersmodal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.sergiobelda.foundry.domain.model.filter.FilterUpdateData
import dev.sergiobelda.foundry.ui.model.filter.FilterUiModel
import dev.sergiobelda.foundry.ui.model.filter.FiltersUiModel
import dev.sergiobelda.foundry.ui.model.filter.FontFamilyCategoryFilterUiModel

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
            ) { filter ->
                FilterItem(
                    filterUiModel = filter,
                    updateFilters = updateFilters
                )
            }
        }
    }
}

@Composable
private fun FilterItem(
    filterUiModel: FilterUiModel<*>,
    updateFilters: (FilterUpdateData) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(filterUiModel.titleStringResId),
            style = MaterialTheme.typography.titleMedium
        )
        FilterContent(
            filterUiModel = filterUiModel,
            updateFilters = updateFilters
        )
    }
}

@Composable
private fun FilterContent(
    filterUiModel: FilterUiModel<*>,
    updateFilters: (FilterUpdateData) -> Unit
) {
    when (filterUiModel) {
        is FontFamilyCategoryFilterUiModel -> {
            FontFamilyCategoryFilterSelector(
                filterUiModel = filterUiModel,
                updateFilters = updateFilters
            )
        }
    }
}