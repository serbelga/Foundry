package dev.sergiobelda.foundry.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.sergiobelda.foundry.data.database.entity.FontFamilyEntity
import dev.sergiobelda.foundry.data.database.entity.FontItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FontFamilyDao {
    @Query("SELECT f.*, " +
            "CASE WHEN s.name IS NULL THEN 0 ELSE 1 END AS saved " +
            "FROM FontFamily f LEFT JOIN SavedFont s ON (f.name = s.name) " +
            "WHERE saved = 1")
    fun getSavedFontItems(): Flow<List<FontItemEntity>>

    @Query("SELECT f.*, " +
            "CASE WHEN s.name IS NULL THEN 0 ELSE 1 END AS saved " +
            "FROM FontFamily f LEFT JOIN SavedFont s ON (f.name = s.name) ")
    fun getFontItems(): Flow<List<FontItemEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg fontFamilyEntity: FontFamilyEntity)
}
