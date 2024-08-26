/*
 * Copyright 2022 Sergio Belda
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
import dev.sergiobelda.foundry.data.database.entity.SavedFontEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedFontsDao {
    @Query("SELECT * FROM SavedFonts")
    fun getSavedFonts(): Flow<List<SavedFontEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(font: SavedFontEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(fonts: List<SavedFontEntity>)

    @Query("DELETE FROM SavedFonts WHERE name = :name")
    suspend fun deleteByName(name: String)

    @Query("DELETE FROM SavedFonts")
    suspend fun clearAll()
}
