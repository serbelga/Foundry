package dev.sergiobelda.foundry.data.database.entity

import androidx.room.Entity

@Entity(
    tableName = "FontFamily",
    primaryKeys = [ "name", "source" ]
)
data class FontFamilyEntity(
    val name: String,
    val category: String,
    val source: FontFamilySource,
)
