package dev.sergiobelda.foundry.ui.main

import dev.sergiobelda.foundry.domain.model.FontItemModel

data class MainUiState(
    val isFetchingFonts: Boolean = false,
    val fontItems: List<FontItemModel> = emptyList(),
    val favoriteFontItems: List<FontItemModel> = emptyList()
)
