package dev.sergiobelda.foundry.ui.main

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

class MainViewModel(
    private val fetchFontsUseCase: FetchFontsUseCase,
    private val getFontItemsUseCase: GetFontItemsUseCase,
    private val insertFavoriteFontUseCase: InsertFavoriteFontUseCase,
    private val removeFavoriteFontUseCase: RemoveFavoriteFontUseCase
) : ViewModel() {

    var mainUiState: MainUiState by mutableStateOf(MainUiState(isFetchingFonts = true))
        private set

    init {
        fetchFonts()
        getFontItems()
    }

    private fun fetchFonts() = viewModelScope.launch {
        fetchFontsUseCase()
        mainUiState = mainUiState.copy(
            isFetchingFonts = false
        )
    }

    private fun getFontItems() = viewModelScope.launch {
        getFontItemsUseCase().collect { fontItems ->
            mainUiState = mainUiState.copy(
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
