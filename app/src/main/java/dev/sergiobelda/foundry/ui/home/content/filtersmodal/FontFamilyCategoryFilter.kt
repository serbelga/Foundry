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
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.sergiobelda.foundry.domain.model.filter.FilterUpdateData
import dev.sergiobelda.foundry.ui.model.filter.FontFamilyCategoryFilterChipUiModel
import dev.sergiobelda.foundry.ui.model.filter.FontFamilyCategoryFilterUiModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun FontFamilyCategoryFilterSelector(
    filterUiModel: FontFamilyCategoryFilterUiModel,
    updateFilters: (FilterUpdateData) -> Unit,
) {
    val filterChips = filterUiModel.toFilterChips {
        updateFilters(it)
    }
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filterChips.forEach {
            FontFamilyCategoryFilterChip(
                filterChipUiModel = it
            )
        }
    }
}

@Composable
private fun FontFamilyCategoryFilterChip(
    filterChipUiModel: FontFamilyCategoryFilterChipUiModel,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = filterChipUiModel.isSelected,
        onClick = filterChipUiModel.onClick,
        label = { Text(text = stringResource(filterChipUiModel.labelStringResId)) },
        modifier = modifier,
        // .animateContentSize(),
        leadingIcon = {
            if (filterChipUiModel.isSelected) {
                Icon(
                    Icons.Rounded.Check,
                    contentDescription = null
                )
            }
        }
    )
}
