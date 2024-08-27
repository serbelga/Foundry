package dev.sergiobelda.foundry.data.database.mapper

import dev.sergiobelda.foundry.data.database.entity.FontFamilyEntity
import dev.sergiobelda.foundry.data.database.entity.FontFamilySource
import dev.sergiobelda.foundry.domain.model.FontFamilyModel
import dev.sergiobelda.foundry.domain.model.GoogleFontFamilyModel

internal fun FontFamilyModel.toFontFamilyEntity(): FontFamilyEntity =
    when (this) {
        is GoogleFontFamilyModel -> {
            FontFamilyEntity(
                name = name,
                category = category,
                source = FontFamilySource.GoogleFonts
            )
        }
    }

internal fun FontFamilyEntity.toFontFamilyModel(): FontFamilyModel =
    when (source) {
        FontFamilySource.GoogleFonts -> {
            GoogleFontFamilyModel(
                name = name,
                category = category
            )
        }
    }
