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

package dev.sergiobelda.foundry.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.sergiobelda.foundry.R
import dev.sergiobelda.foundry.domain.model.FontFamilyItemModel
import dev.sergiobelda.foundry.ui.resolver.resolveFontFamily

@Composable
fun FontFamilyListView(
    listState: LazyListState,
    fonts: List<FontFamilyItemModel>,
    onSaveClick: (FontFamilyItemModel) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        contentPadding = contentPadding,
        state = listState,
        modifier = modifier
            .fillMaxHeight()
    ) {
        items(
            fonts,
            key = { it.fontFamilyModel.family },
            contentType = { it::class },
        ) {
            FontCard(
                it,
                fontFamily = it.fontFamilyModel.resolveFontFamily(),
                onSaveClick = { onSaveClick(it) },
                modifier = Modifier.animateItem()
            )
        }
    }
}

@Composable
fun FontCard(
    fontFamilyItemModel: FontFamilyItemModel,
    fontFamily: FontFamily,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        onClick = {},
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(start = 12.dp, end = 4.dp, bottom = 12.dp, top = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = fontFamilyItemModel.fontFamilyModel.family,
                        style = MaterialTheme.typography.labelMedium,
                    )
                    IconToggleButton(
                        checked = fontFamilyItemModel.isSaved,
                        onCheckedChange = { onSaveClick() }
                    ) {
                        Icon(
                            imageVector = if (fontFamilyItemModel.isSaved) {
                                Icons.Rounded.Bookmark
                            } else {
                                Icons.Rounded.BookmarkBorder
                            },
                            contentDescription = null,
                        )
                    }
                }
                Text(
                    text = stringResource(id = R.string.sample_text),
                    fontSize = 36.sp,
                    fontFamily = fontFamily,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
