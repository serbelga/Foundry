package dev.sergiobelda.foundry.domain.usecase

import dev.sergiobelda.foundry.domain.model.FontModel
import dev.sergiobelda.foundry.domain.repository.IFontsRepository
import dev.sergiobelda.foundry.domain.result.Result

class FetchFontsUseCase(
    private val fontsRepository: IFontsRepository
) {

    suspend operator fun invoke(): Result<List<FontModel>> {
        return fontsRepository.fetchFonts()
    }
}
