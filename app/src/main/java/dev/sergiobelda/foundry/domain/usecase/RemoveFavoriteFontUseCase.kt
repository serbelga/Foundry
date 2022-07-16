package dev.sergiobelda.foundry.domain.usecase

import dev.sergiobelda.foundry.domain.repository.IFontRepository

class RemoveFavoriteFontUseCase(
    private val fontRepository: IFontRepository
) {

    suspend operator fun invoke(favoriteFont: String) {
        fontRepository.removeFavoriteFont(favoriteFont)
    }
}
