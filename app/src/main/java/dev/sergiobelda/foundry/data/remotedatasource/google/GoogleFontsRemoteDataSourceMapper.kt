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

package dev.sergiobelda.foundry.data.remotedatasource.google

import dev.sergiobelda.foundry.data.api.google.model.GoogleFontApiModel
import dev.sergiobelda.foundry.data.remotedatasource.FontsRemoteDataSourceMapper
import dev.sergiobelda.foundry.domain.model.FontFamilyCategory
import dev.sergiobelda.foundry.domain.model.FontFamilyModel
import dev.sergiobelda.foundry.domain.model.FontFamilyProvider

class GoogleFontsRemoteDataSourceMapper : FontsRemoteDataSourceMapper<GoogleFontApiModel> {

    private val googleFontCategories = mapOf(
        "sans-serif" to FontFamilyCategory.SansSerif,
        "serif" to FontFamilyCategory.Serif,
        "display" to FontFamilyCategory.Display,
        "handwriting" to FontFamilyCategory.Handwriting,
        "monospace" to FontFamilyCategory.Monospace,
    )

    override fun map(fontApiModel: GoogleFontApiModel): FontFamilyModel =
        FontFamilyModel(
            family = fontApiModel.family,
            category = googleFontCategories.getOrDefault(
                fontApiModel.category, FontFamilyCategory.Default
            ),
            provider = FontFamilyProvider.GoogleFonts
        )
}
