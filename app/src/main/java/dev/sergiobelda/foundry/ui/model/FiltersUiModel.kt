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

package dev.sergiobelda.foundry.ui.model

import dev.sergiobelda.foundry.domain.model.FilterModel
import dev.sergiobelda.foundry.domain.model.FilterUpdateData
import dev.sergiobelda.foundry.domain.model.FiltersModel
import dev.sergiobelda.foundry.domain.model.FontFamilyCategoryFilterModel

interface FilterUiModel<F : FilterModel> {
    val titleStringResId: Int

    val filterModel: F

    fun toFilterElementChips(
        onClick: (FilterUpdateData) -> Unit
    ): List<FilterElementChip>
}

data class FiltersUiModel(
    val filters: List<FilterUiModel<*>>
) {
    fun toFilterElementChips(
        onClick: (FilterUpdateData) -> Unit
    ): List<FilterElementChip> =
        filters.flatMap { it.toFilterElementChips(onClick) }
}

fun FiltersModel.toFilterUiModels(): FiltersUiModel =
    FiltersUiModel(
        filters = filters.map { filterModel ->
            when (filterModel) {
                is FontFamilyCategoryFilterModel -> FontFamilyCategoryFilterUiModel(filterModel)
            }
        }
    )
