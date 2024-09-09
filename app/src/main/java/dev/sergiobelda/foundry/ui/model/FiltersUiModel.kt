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

import dev.sergiobelda.foundry.domain.model.FilterUpdateData

data class FiltersUiModel(
    val filters: List<FilterUiModel<*>>
) {
    fun toSelectedFilterChips(
        onClick: (FilterUpdateData) -> Unit
    ): List<SelectedFilterChipUiModel> =
        filters.flatMap { it.toSelectedFilterChips(onClick) }
}
