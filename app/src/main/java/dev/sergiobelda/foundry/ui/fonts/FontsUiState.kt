package dev.sergiobelda.foundry.ui.fonts

import dev.sergiobelda.foundry.domain.model.FontItemModel

data class FontsUiState(
    val isFetchingFonts: Boolean = false,
    val fontItems: List<FontItemModel> = emptyList()
)
