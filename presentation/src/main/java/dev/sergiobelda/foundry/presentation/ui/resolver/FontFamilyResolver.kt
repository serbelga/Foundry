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

package dev.sergiobelda.foundry.presentation.ui.resolver

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import dev.sergiobelda.foundry.domain.model.FontFamilyModel
import dev.sergiobelda.foundry.domain.model.FontFamilyProvider
import dev.sergiobelda.foundry.presentation.R

internal fun FontFamilyModel.resolveFontFamily(): FontFamily =
    when (provider) {
        FontFamilyProvider.GoogleFonts -> toGoogleFontFamily()
        FontFamilyProvider.Default -> FontFamily.Default
    }

private fun FontFamilyModel.toGoogleFontFamily(): FontFamily =
    FontFamily(
        Font(
            googleFont = GoogleFont(family),
            fontProvider = GoogleFontProvider.provider
        ),
    )

private object GoogleFontProvider {

    val provider: GoogleFont.Provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs,
    )
}
