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
import dev.sergiobelda.foundry.domain.model.FontFamilyCategory
import dev.sergiobelda.foundry.domain.model.FontFamilyCategoryFilterElementUpdateData
import dev.sergiobelda.foundry.domain.model.FontFamilyCategoryFilterModel

data class FontFamilyCategoryFilterUiModel(
    override val filterModel: FontFamilyCategoryFilterModel
) : FilterUiModel<FontFamilyCategoryFilterModel> {

    @StringRes
    override val titleStringResId: Int = R.string.font_family_category

    override fun toFilterElementChips(
        onClick: (FilterUpdateData) -> Unit
    ): List<FilterElementChipUiModel> =
        filterModel.elements.map { element ->
            FilterElementChipUiModel(
                labelStringResId = element.category.toStringResId(),
                isSelected = element.isSelected,
                onClick = {
                    onClick(
                        FontFamilyCategoryFilterElementUpdateData(
                            category = element.category,
                            isSelected = !element.isSelected
                        )
                    )
                }
            )
        }

    private fun FontFamilyCategory.toStringResId(): Int =
        when (this) {
            FontFamilyCategory.Display -> R.string.display
            FontFamilyCategory.Handwriting -> R.string.handwriting
            FontFamilyCategory.Monospace -> R.string.monospace
            FontFamilyCategory.SansSerif -> R.string.sans_serif
            FontFamilyCategory.Serif -> R.string.serif
            // TODO: Remove
            FontFamilyCategory.Default -> R.string.serif
        }
}
