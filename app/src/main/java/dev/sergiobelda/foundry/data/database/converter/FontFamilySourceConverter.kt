package dev.sergiobelda.foundry.data.database.converter

import androidx.room.TypeConverter
import dev.sergiobelda.foundry.data.database.entity.FontFamilySource

class FontFamilySourceConverter {
    @TypeConverter
    fun fromFontFamilySource(source: FontFamilySource): String {
        return source.name
    }

    // TODO: Add documentation -> Default FontFamilySource.GoogleFonts
    @TypeConverter
    fun toFontFamilySource(name: String): FontFamilySource =
        enumValues<FontFamilySource>().firstOrNull { it.name.equals(name, ignoreCase = true) }
            ?: FontFamilySource.GoogleFonts
}
