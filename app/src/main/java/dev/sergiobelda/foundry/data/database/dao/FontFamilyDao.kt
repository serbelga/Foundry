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
import androidx.room.RawQuery
import androidx.room.RoomRawQuery
import dev.sergiobelda.foundry.data.database.entity.FontFamilyItemEntity
import dev.sergiobelda.foundry.data.database.entity.table.FontFamilyEntity
import dev.sergiobelda.foundry.data.database.entity.table.LikedFontFamilyEntity
import dev.sergiobelda.foundry.domain.model.FontFamilyCategory
import dev.sergiobelda.foundry.domain.model.filter.FiltersModel
import kotlinx.coroutines.flow.Flow

@Dao
abstract class FontFamilyDao {

    fun getFontFamilyItems(
        filters: FiltersModel
    ): Flow<List<FontFamilyItemEntity>> {
        val query = buildString {
            append(FONT_FAMILY_ITEMS_QUERY)

            // TODO: Create a FontFamilyItemsQueryBuilder
//             if (fontFamilyCategories.isNotEmpty()) {
//                 append(
//                     "WHERE f.category IN (${FontFamilyCategory.entries.joinToString(prefix = "'", postfix = "'")})"
//                 )
//             }
        }

        return getFontFamilyItems(
            RoomRawQuery(query)
        )
    }

    @RawQuery(observedEntities = [ FontFamilyEntity::class, LikedFontFamilyEntity::class ])
    protected abstract fun getFontFamilyItems(
        query: RoomRawQuery
    ): Flow<List<FontFamilyItemEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(vararg fontFamilyEntity: FontFamilyEntity)

    @Query("DELETE FROM FontFamily")
    abstract suspend fun clearAll()

    companion object {
        private const val FONT_FAMILY_ITEMS_QUERY =
            "SELECT f.*, " +
                "CASE WHEN s.family IS NULL THEN 0 ELSE 1 END AS isSaved " +
                "FROM FontFamily f LEFT JOIN LikedFontFamily s ON (f.family = s.family) "
    }
}
