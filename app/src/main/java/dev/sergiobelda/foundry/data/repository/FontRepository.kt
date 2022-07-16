package dev.sergiobelda.foundry.data.repository

import dev.sergiobelda.foundry.data.localdatasource.IFontLocalDataSource
import dev.sergiobelda.foundry.data.remotedatasource.IFontRemoteDataSource
import dev.sergiobelda.foundry.domain.model.FontItemModel
import dev.sergiobelda.foundry.domain.repository.IFontRepository
import dev.sergiobelda.foundry.domain.result.doIfSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class FontRepository(
    private val fontRemoteDataSource: IFontRemoteDataSource,
    private val fontLocalDataSource: IFontLocalDataSource
) : IFontRepository {

    override suspend fun fetchFonts() {
        val googleFonts = fontRemoteDataSource.getGoogleFonts()
        googleFonts.doIfSuccess {
            fontLocalDataSource.insertGoogleFonts(it)
        }
    }

    override suspend fun removeFavoriteFont(favoriteFont: String) {
        fontLocalDataSource.removeFavoriteFont(favoriteFont = favoriteFont)
    }

    override suspend fun insertFavoriteFont(favoriteFont: String) {
        fontLocalDataSource.insertFavoriteFont(favoriteFont = favoriteFont)
    }

    override fun getFontItems(): Flow<List<FontItemModel>> =
        fontLocalDataSource.googleFonts.combine(fontLocalDataSource.favoriteFonts) { googleFonts, favoriteFonts ->
            googleFonts.map { googleFont ->
                FontItemModel(
                    googleFont,
                    isFavorite = favoriteFonts.any { it.name == googleFont.name }
                )
            }
        }
}
