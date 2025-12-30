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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sergiobelda.foundry.domain.model.filter.FilterUpdateData
import dev.sergiobelda.foundry.domain.usecase.FetchFontsUseCase
import dev.sergiobelda.foundry.domain.usecase.GetFontFamilyItemsUseCase
import dev.sergiobelda.foundry.domain.usecase.GetSavedFontFamilyItemsUseCase
import dev.sergiobelda.foundry.domain.usecase.LikeFontFamilyUseCase
import dev.sergiobelda.foundry.domain.usecase.RemoveLikedFontFamilyUseCase
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val fetchFontsUseCase: FetchFontsUseCase,
    private val getFontFamilyItemsUseCase: GetFontFamilyItemsUseCase,
    private val getSavedFontFamilyItemsUseCase: GetSavedFontFamilyItemsUseCase,
    private val likeFontFamilyUseCase: LikeFontFamilyUseCase,
    private val removeLikedFontFamilyUseCase: RemoveLikedFontFamilyUseCase,
) : ViewModel() {
    val state: StateFlow<HomeState>
        field = MutableStateFlow(HomeState(isLoadingFonts = true))
    
    init {
        fetchFonts()
        getFontFamilyItems()
        getSavedFontFamilyItems()
    }

    private fun fetchFonts() =
        viewModelScope.launch {
            fetchFontsUseCase()
            state.value = state.value.copy(
                isLoadingFonts = false,
            )
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getFontFamilyItems() =
        viewModelScope.launch {
            state.flatMapLatest {
                getFontFamilyItemsUseCase(it.filters)
            }.collect {
                state.value = state.value.copy(
                    fontItems = it.toPersistentList(),
                )
            }
        }

    private fun getSavedFontFamilyItems() =
        viewModelScope.launch {
            getSavedFontFamilyItemsUseCase().collect { savedFontItems ->
                state.value = state.value.copy(
                    savedFontItems = savedFontItems.toPersistentList(),
                )
            }
        }

    fun updateFontFamilyLikedState(fontFamilyItemModel: dev.sergiobelda.foundry.domain.model.FontFamilyItemModel) =
        viewModelScope.launch {
            // TODO: This check should change to two different use cases, add/remove to
            //  liked font families and add/remove to font family group
            if (fontFamilyItemModel.isSaved) {
                removeLikedFontFamilyUseCase.invoke(name = fontFamilyItemModel.fontFamilyModel.family)
            } else {
                likeFontFamilyUseCase.invoke(name = fontFamilyItemModel.fontFamilyModel.family)
            }
        }

    fun updateFilters(data: FilterUpdateData) {
        state.value = state.value.copy(
            filters = state.value.filters.updateData(data)
        )
    }
}
