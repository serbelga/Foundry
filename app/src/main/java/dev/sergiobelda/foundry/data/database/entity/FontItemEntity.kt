package dev.sergiobelda.foundry.data.database.entity

import androidx.room.Embedded

data class FontItemEntity(
    @Embedded
    val fontFamilyEntity: FontFamilyEntity,
    val saved: Boolean
)
