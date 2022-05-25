package dev.sergiobelda.foundry.data.localdatasource

import dev.sergiobelda.foundry.domain.model.FontModel
import dev.sergiobelda.foundry.domain.result.Result
import kotlinx.coroutines.flow.Flow

interface IFontsLocalDataSource {

    val fonts: Flow<Result<List<FontModel>>>

    suspend fun insertAll(fonts: List<FontModel>)

    suspend fun clearAll()
}
