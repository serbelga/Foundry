package dev.sergiobelda.foundry.data.database.entity

import androidx.room.Embedded

data class FontFamilyItemEntity(
    @Embedded
    val fontFamilyEntity: FontFamilyEntity,
    val saved: Boolean
)
