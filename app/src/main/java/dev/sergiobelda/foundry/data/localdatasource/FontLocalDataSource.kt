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
import dev.sergiobelda.foundry.data.database.dao.LikedFontFamilyDao
import dev.sergiobelda.foundry.data.database.entity.LikedFontFamilyEntity
import dev.sergiobelda.foundry.data.database.mapper.toFontFamilyEntity
import dev.sergiobelda.foundry.data.database.mapper.toFontFamilyModel
import dev.sergiobelda.foundry.domain.model.FontFamilyItemModel
import dev.sergiobelda.foundry.domain.model.FontFamilyModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FontLocalDataSource(
    private val likedFontFamilyDao: LikedFontFamilyDao,
    private val fontFamilyDao: FontFamilyDao
) : IFontLocalDataSource {

    override fun getFontFamilyItems(): Flow<List<FontFamilyItemModel>> =
        fontFamilyDao.getFontFamilyItems().map { list ->
            // TODO: Create mapper
            list.map {
                FontFamilyItemModel(
                    fontFamilyModel = it.fontFamilyEntity.toFontFamilyModel(),
                    isSaved = it.saved
                )
            }
        }

    override fun getSavedFontFamilyItems(): Flow<List<FontFamilyItemModel>> =
        fontFamilyDao.getSavedFontFamilyItems().map { list ->
            // TODO: Create mapper
            list.map {
                FontFamilyItemModel(
                    fontFamilyModel = it.fontFamilyEntity.toFontFamilyModel(),
                    isSaved = it.saved
                )
            }
        }

    override suspend fun insertFonts(fonts: List<FontFamilyModel>) {
        fonts.forEach {
            fontFamilyDao.insert(
                it.toFontFamilyEntity()
            )
        }
    }

    override suspend fun removeSavedFont(name: String) {
        likedFontFamilyDao.deleteByName(name = name)
    }

    override suspend fun saveFont(name: String) {
        likedFontFamilyDao.insert(LikedFontFamilyEntity(name = name))
    }

    override suspend fun clearAllFontFamilyItems() {
        fontFamilyDao.clearAll()
    }

    override suspend fun clearAllSavedFonts() {
        likedFontFamilyDao.clearAll()
    }
}
