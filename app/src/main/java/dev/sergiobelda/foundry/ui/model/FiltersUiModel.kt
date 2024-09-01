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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dev.sergiobelda.foundry.R
import dev.sergiobelda.foundry.domain.model.FilterModel
import dev.sergiobelda.foundry.domain.model.FontFamilyCategory
import dev.sergiobelda.foundry.domain.model.FontFamilyCategoryFilterElementModel
import dev.sergiobelda.foundry.domain.model.FontFamilyCategoryFilterElementUpdateData
import dev.sergiobelda.foundry.domain.model.FontFamilyCategoryFilterModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class FiltersUiModel(
    val filters: ImmutableList<FilterUiModel<*>> = persistentListOf(
        FontFamilyCategoryFilterUiModel()
    )
) {

    fun toFilterElementChips(): List<FilterElementChip> =
        filters.flatMap { it.toFilterElementChips() }
}

class FontFamilyCategoryFilterUiModel : FilterUiModel<FontFamilyCategoryFilterModel> {

    @StringRes override val stringResourceId: Int = R.string.filters

    override var filterModel: MutableState<FontFamilyCategoryFilterModel> =
        mutableStateOf(
            FontFamilyCategoryFilterModel(
                elements = FontFamilyCategory.entries.map {
                    FontFamilyCategoryFilterElementModel(
                        category = it,
                        isSelected = false
                    )
                }
            )
        )

    override fun toFilterElementChips(): List<FilterElementChip> =
        filterModel.value.elements.map {
            FilterElementChip(
                label = it.category.name,
                isSelected = it.isSelected,
                onClick = {
                    filterModel.value = filterModel.value.updateData(
                        FontFamilyCategoryFilterElementUpdateData(
                            category = it.category.name,
                            isSelected = !it.isSelected
                        )
                        // TODO: Not use cast here
                    ) as FontFamilyCategoryFilterModel
                }
            )
        }
}


interface FilterUiModel<F : FilterModel> {
    val stringResourceId: Int

    var filterModel: MutableState<F>

    fun toFilterElementChips(): List<FilterElementChip>
}

data class FilterElementChip(
    val label: String,
    val isSelected: Boolean,
    val onClick: () -> Unit
)
