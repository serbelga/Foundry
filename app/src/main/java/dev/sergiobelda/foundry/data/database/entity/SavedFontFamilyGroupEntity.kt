package dev.sergiobelda.foundry.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SavedFontFamilyGroup")
data class SavedFontFamilyGroupEntity(
    @PrimaryKey
    val name: String
)
