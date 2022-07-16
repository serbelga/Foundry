package dev.sergiobelda.foundry.domain.repository

import dev.sergiobelda.foundry.domain.model.FontItemModel
import kotlinx.coroutines.flow.Flow

interface IFontRepository {

    suspend fun fetchFonts()

    suspend fun removeFavoriteFont(favoriteFont: String)

    suspend fun insertFavoriteFont(favoriteFont: String)

    fun getFontItems(): Flow<List<FontItemModel>>
}
