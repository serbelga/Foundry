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

package dev.sergiobelda.foundry.presentation.ui.home.menu

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.sergiobelda.foundry.presentation.R
import dev.sergiobelda.foundry.presentation.ui.theme.pacificoFontFamily

@Composable
internal fun HomeMenuContent(
    onHomeMenuNavigationItemClick: (HomeMenuNavigationItem) -> Unit,
    homeMenuNavigationItemSelected: HomeMenuNavigationItem,
) { 
    ModalDrawerSheet {
        Text(
            text = stringResource(id = R.string.app_name),
            fontFamily = pacificoFontFamily,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp,
            modifier =  Modifier.padding(
                start = 24.dp,
                top = 8.dp,
                end = 8.dp,
                bottom = 8.dp,
            ),
        )
        HorizontalDivider()
        HomeMenuSpacer()
        HomeMenuNavigationDrawerItem(
            homeMenuNavigationItem = HomeMenuNavigationItem.FontsMenuNavigationItem,
            onClick = { onHomeMenuNavigationItemClick(HomeMenuNavigationItem.FontsMenuNavigationItem) },
            selected = homeMenuNavigationItemSelected == HomeMenuNavigationItem.FontsMenuNavigationItem,
        )
        HomeMenuNavigationDrawerItem(
            homeMenuNavigationItem = HomeMenuNavigationItem.SavedFontsMenuNavigationItem,
            onClick = { onHomeMenuNavigationItemClick(HomeMenuNavigationItem.SavedFontsMenuNavigationItem) },
            selected = homeMenuNavigationItemSelected == HomeMenuNavigationItem.SavedFontsMenuNavigationItem,
        )
        HomeMenuSpacer()
        HorizontalDivider(modifier = Modifier.padding(horizontal = HomeMenuDividerHorizontalPadding))
        HomeMenuSpacer()
        HomeMenuNavigationDrawerItem(
            homeMenuNavigationItem = HomeMenuNavigationItem.SettingsMenuNavigationItem,
            onClick = { onHomeMenuNavigationItemClick(HomeMenuNavigationItem.SettingsMenuNavigationItem) },
            selected = false
        )
    }
}

@Composable
private fun HomeMenuSpacer() {
    Spacer(modifier = Modifier.height(HomeMenuSpacerHeight))
}

@Composable
private fun HomeMenuNavigationDrawerItem(
    homeMenuNavigationItem: HomeMenuNavigationItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) {
    NavigationDrawerItem(
        icon = {
            Icon(imageVector = homeMenuNavigationItem.imageVector, contentDescription = null)
        },
        label = {
            Text(
                text = stringResource(id = homeMenuNavigationItem.stringResourceId),
                style = MaterialTheme.typography.labelLarge,
            )
        },
        selected = selected,
        onClick = onClick,
        modifier = modifier.padding(
            start = HomeMenuNavigationDrawerItemPadding,
            end = HomeMenuNavigationDrawerItemPadding,
        ),
    )
}

private val HomeMenuDividerHorizontalPadding: Dp = 28.dp
private val HomeMenuSpacerHeight: Dp = 12.dp
private val HomeMenuNavigationDrawerItemPadding: Dp = 8.dp
