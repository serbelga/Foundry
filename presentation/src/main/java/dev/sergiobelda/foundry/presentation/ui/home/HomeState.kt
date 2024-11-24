/*
 * Copyright 2022 Sergio Belda
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

package dev.sergiobelda.foundry.presentation.ui.home

import androidx.compose.runtime.Immutable
import dev.sergiobelda.foundry.domain.model.FontFamilyItemModel
import dev.sergiobelda.foundry.domain.model.filter.FiltersModel
import dev.sergiobelda.foundry.domain.model.filter.FontFamilyCategoryFilterModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class HomeState(
    val isLoadingFonts: Boolean = false,
    val isSavedFontsSelected: Boolean = false,
    val fontItems: ImmutableList<FontFamilyItemModel> = persistentListOf(),
    val filters: FiltersModel = FiltersModel(
        filters = persistentListOf(
            FontFamilyCategoryFilterModel()
        )
    ),
    val savedFontItems: ImmutableList<FontFamilyItemModel> = persistentListOf(),
)
