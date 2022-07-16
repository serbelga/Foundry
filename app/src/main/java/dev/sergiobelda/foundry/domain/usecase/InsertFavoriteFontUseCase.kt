package dev.sergiobelda.foundry.domain.usecase

import dev.sergiobelda.foundry.domain.repository.IFontRepository

class InsertFavoriteFontUseCase(
    private val fontRepository: IFontRepository
) {

    suspend operator fun invoke(favoriteFont: String) {
        fontRepository.insertFavoriteFont(favoriteFont)
    }
}
