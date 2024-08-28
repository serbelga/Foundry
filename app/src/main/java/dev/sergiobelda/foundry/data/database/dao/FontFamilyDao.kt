/*
 * Copyright 2024 Sergio Belda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.sergiobelda.foundry.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.sergiobelda.foundry.data.database.entity.FontFamilyEntity
import dev.sergiobelda.foundry.data.database.entity.FontFamilyItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FontFamilyDao {
    // TODO: Return LikedFontFamilies and other custom groups.
    @Query("SELECT f.*, " +
            "CASE WHEN s.family IS NULL THEN 0 ELSE 1 END AS saved " +
            "FROM FontFamily f LEFT JOIN LikedFontFamily s ON (f.family = s.family) " +
            "WHERE saved = 1")
    fun getSavedFontFamilyItems(): Flow<List<FontFamilyItemEntity>>

    @Query("SELECT f.*, " +
            "CASE WHEN s.family IS NULL THEN 0 ELSE 1 END AS saved " +
            "FROM FontFamily f LEFT JOIN LikedFontFamily s ON (f.family = s.family) "
    )
    fun getFontFamilyItems(): Flow<List<FontFamilyItemEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg fontFamilyEntity: FontFamilyEntity)

    @Query("DELETE FROM FontFamily")
    suspend fun clearAll()
}
