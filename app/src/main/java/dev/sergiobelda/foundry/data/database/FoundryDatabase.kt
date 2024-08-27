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

package dev.sergiobelda.foundry.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.sergiobelda.foundry.data.database.converter.FontFamilySourceConverter
import dev.sergiobelda.foundry.data.database.dao.FontFamilyDao
import dev.sergiobelda.foundry.data.database.dao.SavedFontDao
import dev.sergiobelda.foundry.data.database.entity.FontFamilyEntity
import dev.sergiobelda.foundry.data.database.entity.FontFamilySource
import dev.sergiobelda.foundry.data.database.entity.SavedFontEntity

@Database(
    entities = [SavedFontEntity::class, FontFamilyEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(FontFamilySourceConverter::class)
abstract class FoundryDatabase : RoomDatabase() {
    abstract fun fontFamilyDao(): FontFamilyDao

    abstract fun savedFontsDao(): SavedFontDao
}
