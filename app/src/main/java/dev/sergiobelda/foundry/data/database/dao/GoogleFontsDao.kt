package dev.sergiobelda.foundry.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.sergiobelda.foundry.data.database.entity.GoogleFontEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoogleFontsDao {

    @Query("SELECT * FROM GoogleFonts")
    fun getGoogleFonts(): Flow<List<GoogleFontEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(googleFontEntity: GoogleFontEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(googleFontEntities: List<GoogleFontEntity>)
}
