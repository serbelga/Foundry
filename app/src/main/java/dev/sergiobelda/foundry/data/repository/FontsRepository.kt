package dev.sergiobelda.foundry.data.repository

import dev.sergiobelda.foundry.data.localdatasource.IFontsLocalDataSource
import dev.sergiobelda.foundry.data.remotedatasource.IFontsRemoteDataSource
import dev.sergiobelda.foundry.domain.model.FontModel
import dev.sergiobelda.foundry.domain.repository.IFontsRepository
import dev.sergiobelda.foundry.domain.result.Result

class FontsRepository(
    private val fontsRemoteDataSource: IFontsRemoteDataSource,
    private val fontsLocalDataSource: IFontsLocalDataSource
) : IFontsRepository {

    override suspend fun fetchFonts(): Result<List<FontModel>> =
        fontsRemoteDataSource.getGoogleFonts()
}
