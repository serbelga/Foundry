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

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val LightColorScheme =
    lightColorScheme(
        primary = foundry_light_primary,
        onPrimary = foundry_light_onPrimary,
        primaryContainer = foundry_light_primaryContainer,
        onPrimaryContainer = foundry_light_onPrimaryContainer,
        secondary = foundry_light_secondary,
        onSecondary = foundry_light_onSecondary,
        secondaryContainer = foundry_light_secondaryContainer,
        onSecondaryContainer = foundry_light_onSecondaryContainer,
        tertiary = foundry_light_tertiary,
        onTertiary = foundry_light_onTertiary,
        tertiaryContainer = foundry_light_tertiaryContainer,
        onTertiaryContainer = foundry_light_onTertiaryContainer,
        error = foundry_light_error,
        onError = foundry_light_onError,
        errorContainer = foundry_light_errorContainer,
        onErrorContainer = foundry_light_onErrorContainer,
        background = foundry_light_background,
        onBackground = foundry_light_onBackground,
        surface = foundry_light_surface,
        onSurface = foundry_light_onSurface,
        surfaceVariant = foundry_light_surfaceVariant,
        onSurfaceVariant = foundry_light_onSurfaceVariant,
        outline = foundry_light_outline,
        inverseSurface = foundry_light_inverseSurface,
        inverseOnSurface = foundry_light_inverseOnSurface,
        inversePrimary = foundry_light_inversePrimary,
        surfaceTint = foundry_light_surfaceTint,
    )

private val DarkColorScheme =
    darkColorScheme(
        primary = foundry_dark_primary,
        onPrimary = foundry_dark_onPrimary,
        primaryContainer = foundry_dark_primaryContainer,
        onPrimaryContainer = foundry_dark_onPrimaryContainer,
        secondary = foundry_dark_secondary,
        onSecondary = foundry_dark_onSecondary,
        secondaryContainer = foundry_dark_secondaryContainer,
        onSecondaryContainer = foundry_dark_onSecondaryContainer,
        tertiary = foundry_dark_tertiary,
        onTertiary = foundry_dark_onTertiary,
        tertiaryContainer = foundry_dark_tertiaryContainer,
        onTertiaryContainer = foundry_dark_onTertiaryContainer,
        error = foundry_dark_error,
        onError = foundry_dark_onError,
        errorContainer = foundry_dark_errorContainer,
        onErrorContainer = foundry_dark_onErrorContainer,
        background = foundry_dark_background,
        onBackground = foundry_dark_onBackground,
        surface = foundry_dark_surface,
        onSurface = foundry_dark_onSurface,
        surfaceVariant = foundry_dark_surfaceVariant,
        onSurfaceVariant = foundry_dark_onSurfaceVariant,
        outline = foundry_dark_outline,
        inverseSurface = foundry_dark_inverseSurface,
        inverseOnSurface = foundry_dark_inverseOnSurface,
        inversePrimary = foundry_dark_inversePrimary,
        surfaceTint = foundry_dark_surfaceTint,
    )

@Composable
fun FoundryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
