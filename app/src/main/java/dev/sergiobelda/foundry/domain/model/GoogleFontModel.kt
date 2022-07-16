package dev.sergiobelda.foundry.domain.model

data class GoogleFontModel(
    override val name: String,
    val category: String
) : FontModel(name)
