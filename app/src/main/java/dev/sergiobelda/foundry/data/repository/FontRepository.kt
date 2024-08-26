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

import dev.sergiobelda.foundry.data.localdatasource.IFontLocalDataSource
import dev.sergiobelda.foundry.data.remotedatasource.IFontRemoteDataSource
import dev.sergiobelda.foundry.domain.model.FontItemModel
import dev.sergiobelda.foundry.domain.repository.IFontRepository
import dev.sergiobelda.foundry.domain.result.doIfSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class FontRepository(
    private val fontRemoteDataSource: IFontRemoteDataSource,
    private val fontLocalDataSource: IFontLocalDataSource,
) : IFontRepository {
    override suspend fun fetchFonts() {
        // TODO: Rename to getFonts and pass a query to fetch from different sources?
        val googleFonts = fontRemoteDataSource.getGoogleFonts()
        googleFonts.doIfSuccess {
            // TODO: Remove and insertAll?
            fontLocalDataSource.insertGoogleFonts(it)
        }
    }

    override suspend fun removeSavedFont(name: String) {
        fontLocalDataSource.removeSavedFont(name = name)
    }

    override suspend fun saveFont(name: String) {
        fontLocalDataSource.saveFont(name = name)
    }

    override fun getFontItems(): Flow<List<FontItemModel>> =
        fontLocalDataSource.googleFonts.combine(fontLocalDataSource.savedFonts) { googleFonts, savedFonts ->
            googleFonts.map { googleFont ->
                FontItemModel(
                    googleFont,
                    isSaved = savedFonts.any { it.name == googleFont.name },
                )
            }
        }
}
