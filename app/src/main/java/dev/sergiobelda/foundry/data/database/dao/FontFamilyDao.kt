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
import dev.sergiobelda.foundry.domain.model.filter.FiltersModel
import dev.sergiobelda.foundry.domain.model.filter.FontFamilyCategoryFilterModel
import kotlinx.coroutines.flow.Flow

@Dao
abstract class FontFamilyDao {

    // TODO: Consider replace FiltersModel by list List<FilterModel>
    fun getFontFamilyItems(
        filters: FiltersModel
    ): Flow<List<FontFamilyItemEntity>> {
        val query = buildString {
            append(FONT_FAMILY_ITEMS_QUERY)

            // TODO: Improve this code
            FontFamilyItemsQueryBuilder.getConditions(filters).forEachIndexed { index, condition ->
                if (index == 0) {
                    append("WHERE ")
                }
                append(condition)
            }
        }

        return getFontFamilyItems(
            RoomRawQuery(query)
        )
    }

    @RawQuery(observedEntities = [FontFamilyEntity::class, LikedFontFamilyEntity::class])
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
                    "CASE WHEN l.family IS NULL THEN 0 ELSE 1 END AS isSaved " +
                    "FROM FontFamily f LEFT JOIN LikedFontFamily l ON (f.family = l.family) "
    }
}

private object FontFamilyItemsQueryBuilder {

    // TODO: Improve this code
    fun getConditions(
        filters: FiltersModel,
    ): List<String> = buildList {
        filters.filters.forEach {
            when (it) {
                is FontFamilyCategoryFilterModel -> {
                    it.elements.filter { it.isSelected }.takeIf { it.isNotEmpty() }?.let { elements ->
                        val condition =
                            "f.category IN (" +
                                    elements
                                        .map { it.category }
                                        .joinToString(prefix = "'", postfix = "'", separator = "', '") +
                                    ")"
                        add(condition)
                    }
                }
            }
        }
    }
}
