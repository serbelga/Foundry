package dev.sergiobelda.foundry.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Fonts")
data class FontEntity(
    @PrimaryKey
    val name: String
)
