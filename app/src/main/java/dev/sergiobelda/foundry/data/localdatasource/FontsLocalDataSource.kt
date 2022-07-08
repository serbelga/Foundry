package dev.sergiobelda.foundry.data.localdatasource

import dev.sergiobelda.foundry.data.database.dao.FontsDao
import dev.sergiobelda.foundry.data.database.entity.FontEntity
import dev.sergiobelda.foundry.domain.model.FontModel
import dev.sergiobelda.foundry.domain.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FontsLocalDataSource(
    private val fontsDao: FontsDao
) : IFontsLocalDataSource {

    override val fonts: Flow<Result<List<FontModel>>> = fontsDao.getFonts().map { list ->
        Result.Success(list.map { FontModel(it.name, isFavorite = true) })
    }

    override suspend fun insertAll(fonts: List<FontModel>) {
        fontsDao.insertAll(fonts.map { FontEntity(it.name) })
    }

    override suspend fun clearAll() {
        fontsDao.clearAll()
    }
}
