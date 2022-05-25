package dev.sergiobelda.foundry.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.sergiobelda.foundry.data.database.entity.FontEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FontsDao {

    @Query("SELECT * FROM fonts")
    fun getFonts(): Flow<List<FontEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(font: FontEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(fonts: List<FontEntity>)

    @Query("DELETE FROM fonts")
    suspend fun clearAll()
}
