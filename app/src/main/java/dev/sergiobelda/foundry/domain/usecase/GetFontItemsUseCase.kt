package dev.sergiobelda.foundry.domain.usecase

import dev.sergiobelda.foundry.domain.model.FontItemModel
import dev.sergiobelda.foundry.domain.repository.IFontRepository
import kotlinx.coroutines.flow.Flow

class GetFontItemsUseCase(
    private val fontRepository: IFontRepository
) {

    operator fun invoke(): Flow<List<FontItemModel>> =
        fontRepository.getFontItems()
}
