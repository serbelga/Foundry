package dev.sergiobelda.foundry.ui.favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sergiobelda.foundry.domain.model.FontItemModel
import dev.sergiobelda.foundry.domain.usecase.GetFontItemsUseCase
import dev.sergiobelda.foundry.domain.usecase.InsertFavoriteFontUseCase
import dev.sergiobelda.foundry.domain.usecase.RemoveFavoriteFontUseCase
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFontItemsUseCase: GetFontItemsUseCase,
    private val insertFavoriteFontUseCase: InsertFavoriteFontUseCase,
    private val removeFavoriteFontUseCase: RemoveFavoriteFontUseCase
) : ViewModel() {

    var favoritesUiState: FavoritesUiState by mutableStateOf(FavoritesUiState())
        private set

    init {
        getFontItems()
    }

    private fun getFontItems() = viewModelScope.launch {
        getFontItemsUseCase().collect { fontItems ->
            favoritesUiState = favoritesUiState.copy(
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
