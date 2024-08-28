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

package dev.sergiobelda.foundry.data.remotedatasource

import dev.sergiobelda.foundry.data.api.safeApiCall
import dev.sergiobelda.foundry.data.api.google.service.GoogleFontsApiService
import dev.sergiobelda.foundry.domain.model.FontFamilyModel
import dev.sergiobelda.foundry.domain.model.FontFamilyProvider
import dev.sergiobelda.foundry.domain.result.Result

// TODO: Refactor to fetch from multiple sources
class FontRemoteDataSource(
    private val googleFontsApiService: GoogleFontsApiService,
) : IFontRemoteDataSource {
    override suspend fun getFonts(): Result<List<FontFamilyModel>> =
        safeApiCall {
            googleFontsApiService.getGoogleFonts()
        }.map {
            response -> response.items.map {
                // TODO: Create mapper
                FontFamilyModel(it.family, it.category, FontFamilyProvider.GoogleFonts)
            }
        }
}
