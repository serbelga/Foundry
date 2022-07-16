package dev.sergiobelda.foundry.data.localdatasource

import dev.sergiobelda.foundry.domain.model.FavoriteFontModel
import dev.sergiobelda.foundry.domain.model.GoogleFontModel
import kotlinx.coroutines.flow.Flow

interface IFontLocalDataSource {

    val favoriteFonts: Flow<List<FavoriteFontModel>>

    val googleFonts: Flow<List<GoogleFontModel>>

    suspend fun insertGoogleFonts(googleFonts: List<GoogleFontModel>)

    suspend fun removeFavoriteFont(favoriteFont: String)

    suspend fun insertFavoriteFont(favoriteFont: String)

    suspend fun clearAllFavoriteFonts()
}
