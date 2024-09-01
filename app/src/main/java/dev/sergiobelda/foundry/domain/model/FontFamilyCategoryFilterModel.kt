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

package dev.sergiobelda.foundry.domain.model

data class FontFamilyCategoryFilterModel(
    val elements: List<FontFamilyCategoryFilterElementModel> =
        FontFamilyCategory.entries.map {
            FontFamilyCategoryFilterElementModel(
                category = it,
                isSelected = false
            )
        }
) : FilterModel {

    override fun updateData(data: FilterUpdateData): FilterModel =
        // TODO: Not use cast here
        (data as? FontFamilyCategoryFilterElementUpdateData)?.let {
            this.copy(
                elements = elements.map {
                    if (it.category == data.category) {
                        it.copy(isSelected = data.isSelected)
                    } else {
                        it
                    }
                }
            )
        } ?: this
}

data class FontFamilyCategoryFilterElementModel(
    val category: FontFamilyCategory,
    val isSelected: Boolean
) : FilterElementModel

data class FontFamilyCategoryFilterElementUpdateData(
    val category: FontFamilyCategory,
    val isSelected: Boolean
) : FilterUpdateData
