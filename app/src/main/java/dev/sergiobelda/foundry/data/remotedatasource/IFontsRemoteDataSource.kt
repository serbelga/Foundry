package dev.sergiobelda.foundry.data.remotedatasource

import dev.sergiobelda.foundry.domain.model.FontModel
import dev.sergiobelda.foundry.domain.result.Result

interface IFontsRemoteDataSource {

    suspend fun getGoogleFonts(): Result<List<FontModel>>
}
