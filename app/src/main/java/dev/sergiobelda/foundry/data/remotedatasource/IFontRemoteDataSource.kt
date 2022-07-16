package dev.sergiobelda.foundry.data.remotedatasource

import dev.sergiobelda.foundry.domain.model.GoogleFontModel
import dev.sergiobelda.foundry.domain.result.Result

interface IFontRemoteDataSource {

    suspend fun getGoogleFonts(): Result<List<GoogleFontModel>>
}
