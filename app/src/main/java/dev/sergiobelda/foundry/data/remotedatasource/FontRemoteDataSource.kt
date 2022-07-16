package dev.sergiobelda.foundry.data.remotedatasource

import dev.sergiobelda.foundry.data.api.safeApiCall
import dev.sergiobelda.foundry.data.api.service.GoogleFontsApiService
import dev.sergiobelda.foundry.domain.model.GoogleFontModel
import dev.sergiobelda.foundry.domain.result.Result

class FontRemoteDataSource(
    private val googleFontsApiService: GoogleFontsApiService
) : IFontRemoteDataSource {

    override suspend fun getGoogleFonts(): Result<List<GoogleFontModel>> = safeApiCall {
        googleFontsApiService.getGoogleFonts()
    }.map { response -> response.items.map { GoogleFontModel(it.family, it.category) } }
}
