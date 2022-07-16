package dev.sergiobelda.foundry.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteFonts")
data class FavoriteFontEntity(
    @PrimaryKey
    val name: String
)
