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

package dev.sergiobelda.foundry.data.repository

import dev.sergiobelda.foundry.data.localdatasource.FontLocalDataSource
import dev.sergiobelda.foundry.data.remotedatasource.FontRemoteDataSource
import dev.sergiobelda.foundry.domain.model.FontFamilyItemModel
import dev.sergiobelda.foundry.domain.model.filter.FiltersModel
import dev.sergiobelda.foundry.domain.repository.FontRepository
import dev.sergiobelda.foundry.domain.result.doIfSuccess
import kotlinx.coroutines.flow.Flow

class FontRepositoryImpl(
    private val fontRemoteDataSource: FontRemoteDataSource,
    private val fontLocalDataSource: FontLocalDataSource,
) : FontRepository {
    override suspend fun fetchFonts() {
        val result = fontRemoteDataSource.getFonts()
        result.doIfSuccess {
            fontLocalDataSource.clearAllFontFamilyItems()
            fontLocalDataSource.insertFontFamilies(it)
        }
    }

    override suspend fun removeLikedFontFamily(family: String) {
        fontLocalDataSource.removeLikedFontFamily(family = family)
    }

    override suspend fun addLikedFontFamily(family: String) {
        fontLocalDataSource.addLikedFontFamily(family = family)
    }

    override fun getFontFamilyItems(
        filters: FiltersModel
    ): Flow<List<FontFamilyItemModel>> =
        fontLocalDataSource.getFontFamilyItems(filters = filters)

    override fun getSavedFontFamilyItems(): Flow<List<FontFamilyItemModel>> =
        fontLocalDataSource.getSavedFontFamilyItems()
}
