package dev.sergiobelda.foundry.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.sergiobelda.foundry.data.database.entity.FavoriteFontEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteFontsDao {

    @Query("SELECT * FROM FavoriteFonts")
    fun getFavoriteFonts(): Flow<List<FavoriteFontEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(font: FavoriteFontEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(fonts: List<FavoriteFontEntity>)

    @Query("DELETE FROM FavoriteFonts WHERE name = :name")
    suspend fun deleteByName(name: String)

    @Query("DELETE FROM FavoriteFonts")
    suspend fun clearAll()
}
