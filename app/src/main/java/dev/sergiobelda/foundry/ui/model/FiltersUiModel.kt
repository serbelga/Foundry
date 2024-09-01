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

import androidx.annotation.StringRes
import dev.sergiobelda.foundry.R
import dev.sergiobelda.foundry.domain.model.FilterUpdateData
import dev.sergiobelda.foundry.domain.model.FilterModel
import dev.sergiobelda.foundry.domain.model.FiltersModel
import dev.sergiobelda.foundry.domain.model.FontFamilyCategoryFilterModel

fun FiltersModel.toFilterUiModels(): List<FilterUiModel<*>> =
    filters.map { filterModel ->
        when (filterModel) {
            is FontFamilyCategoryFilterModel -> FontFamilyCategoryFilterUiModel(filterModel)
        }
    }

data class FontFamilyCategoryFilterUiModel(
    override val filterModel: FontFamilyCategoryFilterModel
) : FilterUiModel<FontFamilyCategoryFilterModel> {

    @StringRes override val stringResourceId: Int = R.string.filters

    override fun toFilterElementChips(
        onClick: (FilterUpdateData) -> Unit
    ): List<FilterElementChip> =
        filterModel.elements.map { element ->
            FontFamilyCategoryFilterElementChip(
                label = element.category.name,
                isSelected = element.isSelected,
                onClick = onClick
            )
        }
}

data class FontFamilyCategoryFilterElementChip(
    override val label: String,
    override val isSelected: Boolean,
    override val onClick: (FilterUpdateData) -> Unit
) : FilterElementChip

interface FilterUiModel<F : FilterModel> {
    val stringResourceId: Int

    val filterModel: F

    fun toFilterElementChips(
        onClick: (FilterUpdateData) -> Unit
    ): List<FilterElementChip>
}

sealed interface FilterElementChip {
    val label: String

    val isSelected: Boolean

    val onClick: (FilterUpdateData) -> Unit
}
