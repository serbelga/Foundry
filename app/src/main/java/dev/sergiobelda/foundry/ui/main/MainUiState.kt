package dev.sergiobelda.foundry.ui.main

import dev.sergiobelda.foundry.domain.model.FontModel

data class MainUiState(
    val isFetchingFonts: Boolean = false,
    val fonts: List<FontModel> = emptyList()
)
