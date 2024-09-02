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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.sergiobelda.foundry.domain.model.FilterUpdateData
import dev.sergiobelda.foundry.ui.model.FilterElementChipUiModel
import dev.sergiobelda.foundry.ui.model.FiltersUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FiltersModalBottomSheet(
    filters: FiltersUiModel,
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
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(
                // TODO: Use key
                // key = { item -> item },
                items = filters.toFilterElementChips {
                    updateFilters(it)
                }
            ) { item  ->
                FilterInputChip(
                    filterInputElementChip = item,
                    modifier = Modifier.animateItem()
                )
            }
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
