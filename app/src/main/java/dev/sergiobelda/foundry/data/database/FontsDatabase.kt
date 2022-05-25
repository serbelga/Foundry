package dev.sergiobelda.foundry.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.sergiobelda.foundry.data.database.dao.FontsDao
import dev.sergiobelda.foundry.data.database.entity.FontEntity

@Database(
    entities = [FontEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FontsDatabase : RoomDatabase() {

    abstract fun fontsDao(): FontsDao
}
