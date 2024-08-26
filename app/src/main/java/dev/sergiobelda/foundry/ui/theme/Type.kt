/*
 * Copyright 2022 Sergio Belda Galbis
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

package dev.sergiobelda.foundry.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.sergiobelda.foundry.R

val lexendFontFamily =
    FontFamily(
        Font(R.font.lexend_light, FontWeight.W300),
        Font(R.font.lexend_regular, FontWeight.W400),
    )

val pacificoFontFamily =
    FontFamily(
        Font(R.font.pacifico_regular, FontWeight.W400),
    )

val Typography =
    Typography(
        displayLarge =
            TextStyle(
                fontFamily = lexendFontFamily,
                fontSize = 57.sp,
            ),
        displayMedium =
            TextStyle(
                fontFamily = lexendFontFamily,
                fontSize = 45.sp,
            ),
        displaySmall =
            TextStyle(
                fontFamily = lexendFontFamily,
                fontSize = 36.sp,
            ),
        headlineLarge =
            TextStyle(
                fontFamily = lexendFontFamily,
                fontSize = 32.sp,
            ),
        headlineMedium =
            TextStyle(
                fontFamily = lexendFontFamily,
                fontSize = 28.sp,
            ),
        headlineSmall =
            TextStyle(
                fontFamily = lexendFontFamily,
                fontSize = 24.sp,
            ),
        titleLarge =
            TextStyle(
                fontFamily = lexendFontFamily,
                fontSize = 22.sp,
            ),
        titleMedium =
            TextStyle(
                fontFamily = lexendFontFamily,
                fontSize = 16.sp,
            ),
        titleSmall =
            TextStyle(
                fontFamily = lexendFontFamily,
                fontSize = 14.sp,
            ),
        labelLarge =
            TextStyle(
                fontFamily = lexendFontFamily,
                fontSize = 14.sp,
            ),
        labelMedium =
            TextStyle(
                fontFamily = lexendFontFamily,
                fontSize = 12.sp,
            ),
        labelSmall =
            TextStyle(
                fontFamily = lexendFontFamily,
                fontSize = 11.sp,
            ),
        bodyLarge =
            TextStyle(
                fontFamily = lexendFontFamily,
                fontSize = 16.sp,
            ),
        bodyMedium =
            TextStyle(
                fontFamily = lexendFontFamily,
                fontSize = 14.sp,
            ),
        bodySmall =
            TextStyle(
                fontFamily = lexendFontFamily,
                fontSize = 12.sp,
            ),
    )
