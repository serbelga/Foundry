package dev.sergiobelda.foundry.domain.repository

import dev.sergiobelda.foundry.domain.model.FontModel
import dev.sergiobelda.foundry.domain.result.Result

interface IFontsRepository {

    suspend fun fetchFonts(): Result<List<FontModel>>
}
