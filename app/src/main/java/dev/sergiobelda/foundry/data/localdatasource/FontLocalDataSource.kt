package dev.sergiobelda.foundry.data.localdatasource

import dev.sergiobelda.foundry.data.database.dao.FavoriteFontsDao
import dev.sergiobelda.foundry.data.database.dao.GoogleFontsDao
import dev.sergiobelda.foundry.data.database.entity.FavoriteFontEntity
import dev.sergiobelda.foundry.data.database.entity.GoogleFontEntity
import dev.sergiobelda.foundry.domain.model.FavoriteFontModel
import dev.sergiobelda.foundry.domain.model.GoogleFontModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FontLocalDataSource(
    private val favoriteFontsDao: FavoriteFontsDao,
    private val googleFontsDao: GoogleFontsDao
) : IFontLocalDataSource {

    // TODO: Create mapper
    override val favoriteFonts: Flow<List<FavoriteFontModel>> =
        favoriteFontsDao.getFavoriteFonts().map { list ->
            list.map { FavoriteFontModel(it.name) }
        }

    // TODO: Create mapper
    override val googleFonts: Flow<List<GoogleFontModel>> =
        googleFontsDao.getGoogleFonts().map { list ->
            list.map { GoogleFontModel(name = it.family, category = it.category) }
        }

    override suspend fun insertGoogleFonts(googleFonts: List<GoogleFontModel>) {
        googleFontsDao.insertAll(
            googleFonts.map { GoogleFontEntity(family = it.name, category = it.category) }
        )
    }

    override suspend fun removeFavoriteFont(favoriteFont: String) {
        favoriteFontsDao.deleteByName(name = favoriteFont)
    }

    override suspend fun insertFavoriteFont(favoriteFont: String) {
        favoriteFontsDao.insert(FavoriteFontEntity(name = favoriteFont))
    }

    override suspend fun clearAllFavoriteFonts() {
        favoriteFontsDao.clearAll()
    }
}
