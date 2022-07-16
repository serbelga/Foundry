package dev.sergiobelda.foundry.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GoogleFonts")
data class GoogleFontEntity(
    @PrimaryKey
    val family: String,
    val category: String
)
