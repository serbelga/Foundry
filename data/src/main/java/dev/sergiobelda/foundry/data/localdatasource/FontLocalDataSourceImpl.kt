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

package dev.sergiobelda.foundry.data.localdatasource

import dev.sergiobelda.foundry.data.database.dao.FontFamilyDao
import dev.sergiobelda.foundry.data.database.dao.SavedFontFamilyDao
import dev.sergiobelda.foundry.data.database.entity.table.LikedFontFamilyEntity
import dev.sergiobelda.foundry.data.localdatasource.mapper.toFontFamilyEntity
import dev.sergiobelda.foundry.data.localdatasource.mapper.toFontFamilyModel
import dev.sergiobelda.foundry.domain.model.FontFamilyItemModel
import dev.sergiobelda.foundry.domain.model.FontFamilyModel
import dev.sergiobelda.foundry.domain.model.filter.FiltersModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FontLocalDataSourceImpl(
    private val savedFontFamilyDao: SavedFontFamilyDao,
    private val fontFamilyDao: FontFamilyDao
) : FontLocalDataSource {

    override fun getFontFamilyItems(
        filters: FiltersModel
    ): Flow<List<FontFamilyItemModel>> =
        fontFamilyDao.getFontFamilyItems(
            filters = filters
        ).map { list ->
            // TODO: Create mapper
            list.map {
                FontFamilyItemModel(
                    fontFamilyModel = it.fontFamilyEntity.toFontFamilyModel(),
                    isSaved = it.isSaved
                )
            }
        }

    override fun getSavedFontFamilyItems(): Flow<List<FontFamilyItemModel>> =
        savedFontFamilyDao.getSavedFontFamilyItems().map { list ->
            // TODO: Create mapper
            list.map {
                FontFamilyItemModel(
                    fontFamilyModel = it.fontFamilyEntity.toFontFamilyModel(),
                    isSaved = it.isSaved
                )
            }
        }

    override suspend fun insertFontFamilies(fonts: List<FontFamilyModel>) {
        fonts.forEach {
            fontFamilyDao.insert(
                it.toFontFamilyEntity()
            )
        }
    }

    override suspend fun removeLikedFontFamily(family: String) {
        savedFontFamilyDao.removeLikedFontFamily(family = family)
    }

    override suspend fun addLikedFontFamily(family: String) {
        savedFontFamilyDao.addLikedFontFamily(LikedFontFamilyEntity(family = family))
    }

    override suspend fun clearAllFontFamilyItems() {
        fontFamilyDao.clearAll()
    }
}
