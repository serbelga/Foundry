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

package dev.sergiobelda.foundry.ui.components

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.sergiobelda.foundry.R
import dev.sergiobelda.foundry.domain.model.FontItemModel

@Composable
fun FontListView(
    listState: LazyListState,
    fonts: List<FontItemModel>,
    provider: GoogleFont.Provider,
    onFavoriteClick: (FontItemModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxHeight()
    ) {
        items(
            fonts,
            key = { it.fontModel.name },
            contentType = { it::class },
        ) {
            val fontName = GoogleFont(it.fontModel.name)
            val fontFamily =
                FontFamily(
                    Font(googleFont = fontName, fontProvider = provider),
                )

            FontCard(
                it,
                fontFamily = fontFamily,
                onFavoriteClick = { onFavoriteClick(it) },
                modifier = Modifier.animateItem()
            )
        }
    }
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun FontCard(
    fontItemModel: FontItemModel,
    fontFamily: FontFamily,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val avdHeartFill =
        AnimatedImageVector.animatedVectorResource(R.drawable.avd_heart_fill)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = {},
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = fontItemModel.fontModel.name,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 12.dp),
                )
                Text(
                    text = stringResource(id = R.string.sample_text),
                    fontSize = 36.sp,
                    fontFamily = fontFamily,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.align(Alignment.TopEnd),
            ) {
                Icon(
                    painter =
                    rememberAnimatedVectorPainter(
                        avdHeartFill,
                        fontItemModel.isFavorite,
                    ),
                    contentDescription = null,
                    tint =
                    if (fontItemModel.isFavorite) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                )
            }
        }
    }
}
