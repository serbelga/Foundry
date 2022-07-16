package dev.sergiobelda.foundry.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.sergiobelda.foundry.data.database.dao.FavoriteFontsDao
import dev.sergiobelda.foundry.data.database.dao.GoogleFontsDao
import dev.sergiobelda.foundry.data.database.entity.FavoriteFontEntity
import dev.sergiobelda.foundry.data.database.entity.GoogleFontEntity

@Database(
    entities = [FavoriteFontEntity::class, GoogleFontEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FoundryDatabase : RoomDatabase() {

    abstract fun favoriteFontsDao(): FavoriteFontsDao

    abstract fun googleFontsDao(): GoogleFontsDao
}
