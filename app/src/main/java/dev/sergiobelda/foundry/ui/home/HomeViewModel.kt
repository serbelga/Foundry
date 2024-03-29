/*
 * Copyright 2022 Sergio Belda Galbis
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
import dev.sergiobelda.foundry.domain.model.FontItemModel
import dev.sergiobelda.foundry.domain.usecase.FetchFontsUseCase
import dev.sergiobelda.foundry.domain.usecase.GetFontItemsUseCase
import dev.sergiobelda.foundry.domain.usecase.InsertFavoriteFontUseCase
import dev.sergiobelda.foundry.domain.usecase.RemoveFavoriteFontUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val fetchFontsUseCase: FetchFontsUseCase,
    private val getFontItemsUseCase: GetFontItemsUseCase,
    private val insertFavoriteFontUseCase: InsertFavoriteFontUseCase,
    private val removeFavoriteFontUseCase: RemoveFavoriteFontUseCase
) : ViewModel() {

    var homeUiState: HomeUiState by mutableStateOf(HomeUiState(isFetchingFonts = true))
        private set

    init {
        fetchFonts()
        getFontItems()
    }

    private fun fetchFonts() = viewModelScope.launch {
        fetchFontsUseCase()
        homeUiState = homeUiState.copy(
            isFetchingFonts = false
        )
    }

    private fun getFontItems() = viewModelScope.launch {
        getFontItemsUseCase().collect { fontItems ->
            homeUiState = homeUiState.copy(
                fontItems = fontItems,
                favoriteFontItems = fontItems.filter { it.isFavorite }
            )
        }
    }

    fun updateFontFavoriteState(fontItemModel: FontItemModel) = viewModelScope.launch {
        if (fontItemModel.isFavorite) {
            removeFavoriteFontUseCase.invoke(favoriteFont = fontItemModel.fontModel.name)
        } else {
            insertFavoriteFontUseCase.invoke(favoriteFont = fontItemModel.fontModel.name)
        }
    }
}
