package dev.sergiobelda.foundry.data.remotedatasource

import dev.sergiobelda.foundry.data.api.safeApiCall
import dev.sergiobelda.foundry.data.api.service.GoogleFontsApiService
import dev.sergiobelda.foundry.domain.model.FontModel
import dev.sergiobelda.foundry.domain.result.Result

class FontsRemoteDataSource(
    private val googleFontsApiService: GoogleFontsApiService
) : IFontsRemoteDataSource {

    override suspend fun getGoogleFonts(): Result<List<FontModel>> = safeApiCall {
        googleFontsApiService.getGoogleFonts()
    }.map { response -> response.items.map { FontModel(it.family, isFavorite = true) } }
}
