package dev.sergiobelda.foundry.ui.favorites

import dev.sergiobelda.foundry.domain.model.FontItemModel

data class FavoritesUiState(
    val favoriteFontItems: List<FontItemModel> = emptyList()
)
