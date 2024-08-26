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

import dev.sergiobelda.foundry.data.database.dao.SavedFontsDao
import dev.sergiobelda.foundry.data.database.dao.GoogleFontsDao
import dev.sergiobelda.foundry.data.database.entity.SavedFontEntity
import dev.sergiobelda.foundry.data.database.entity.GoogleFontEntity
import dev.sergiobelda.foundry.domain.model.SavedFontModel
import dev.sergiobelda.foundry.domain.model.GoogleFontModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FontLocalDataSource(
    private val savedFontsDao: SavedFontsDao,
    private val googleFontsDao: GoogleFontsDao,
) : IFontLocalDataSource {
    // TODO: Create mapper
    override val savedFonts: Flow<List<SavedFontModel>> =
        savedFontsDao.getSavedFonts().map { list ->
            list.map { SavedFontModel(it.name) }
        }

    // TODO: Create mapper
    override val googleFonts: Flow<List<GoogleFontModel>> =
        googleFontsDao.getGoogleFonts().map { list ->
            list.map { GoogleFontModel(name = it.family, category = it.category) }
        }

    override suspend fun insertGoogleFonts(googleFonts: List<GoogleFontModel>) {
        googleFontsDao.insertAll(
            googleFonts.map { GoogleFontEntity(family = it.name, category = it.category) },
        )
    }

    override suspend fun removeSavedFont(name: String) {
        savedFontsDao.deleteByName(name = name)
    }

    override suspend fun saveFont(name: String) {
        savedFontsDao.insert(SavedFontEntity(name = name))
    }

    override suspend fun clearAllSavedFonts() {
        savedFontsDao.clearAll()
    }
}
