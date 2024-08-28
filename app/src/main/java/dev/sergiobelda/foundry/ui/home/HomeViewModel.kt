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

package dev.sergiobelda.foundry.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sergiobelda.foundry.domain.model.FontFamilyItemModel
import dev.sergiobelda.foundry.domain.usecase.FetchFontsUseCase
import dev.sergiobelda.foundry.domain.usecase.GetFontFamilyItemsUseCase
import dev.sergiobelda.foundry.domain.usecase.GetSavedFontFamilyItemsUseCase
import dev.sergiobelda.foundry.domain.usecase.RemoveSavedFontUseCase
import dev.sergiobelda.foundry.domain.usecase.SaveFontUseCase
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

class HomeViewModel(
    private val fetchFontsUseCase: FetchFontsUseCase,
    private val getFontFamilyItemsUseCase: GetFontFamilyItemsUseCase,
    private val getSavedFontFamilyItemsUseCase: GetSavedFontFamilyItemsUseCase,
    private val saveFontUseCase: SaveFontUseCase,
    private val removeSavedFontUseCase: RemoveSavedFontUseCase,
) : ViewModel() {
    var state: HomeState by mutableStateOf(HomeState(isLoadingFonts = true))
        private set

    init {
        fetchFonts()
        getFontFamilyItems()
        getSavedFontFamilyItems()
    }

    private fun fetchFonts() =
        viewModelScope.launch {
            fetchFontsUseCase()
            state =
                state.copy(
                    isLoadingFonts = false,
                )
        }

    private fun getFontFamilyItems() =
        viewModelScope.launch {
            getFontFamilyItemsUseCase().collect { fontItems ->
                state = state.copy(
                    fontItems = fontItems.toPersistentList(),
                )
            }
        }

    private fun getSavedFontFamilyItems() =
        viewModelScope.launch {
            getSavedFontFamilyItemsUseCase().collect { savedFontItems ->
                state = state.copy(
                    savedFontItems = savedFontItems.toPersistentList(),
                )
            }
        }

    fun updateFontSavedState(fontFamilyItemModel: FontFamilyItemModel) =
        viewModelScope.launch {
            if (fontFamilyItemModel.isSaved) {
                removeSavedFontUseCase.invoke(name = fontFamilyItemModel.fontFamilyModel.family)
            } else {
                saveFontUseCase.invoke(name = fontFamilyItemModel.fontFamilyModel.family)
            }
        }
}
