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

package dev.sergiobelda.foundry.data.database.converter

import androidx.room.TypeConverter
import dev.sergiobelda.foundry.domain.model.FontFamilyProvider

internal class FontFamilyProviderConverter {
    @TypeConverter
    fun fromFontFamilyProvider(provider: FontFamilyProvider): String {
        return provider.name
    }

    // TODO: Add documentation -> Default FontFamilyProvider.Default
    @TypeConverter
    fun toFontFamilyProvider(name: String): FontFamilyProvider =
        enumValues<FontFamilyProvider>().firstOrNull { it.name.equals(name, ignoreCase = true) }
            ?: FontFamilyProvider.Default
}
