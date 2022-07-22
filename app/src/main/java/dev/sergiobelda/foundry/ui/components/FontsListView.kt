package dev.sergiobelda.foundry.ui.components

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.sergiobelda.foundry.R
import dev.sergiobelda.foundry.domain.model.FontItemModel

@OptIn(
    ExperimentalTextApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationGraphicsApi::class
)
@Composable
fun FontListView(
    listState: LazyListState,
    fonts: List<FontItemModel>,
    provider: GoogleFont.Provider,
    onFavoriteClick: (FontItemModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = listState,
        modifier = modifier
    ) {
        items(fonts) {
            val fontName = GoogleFont(it.fontModel.name)
            val fontFamily = FontFamily(
                Font(googleFont = fontName, fontProvider = provider)
            )

            val avdHeartFill =
                AnimatedImageVector.animatedVectorResource(R.drawable.avd_heart_fill)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { }
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = it.fontModel.name,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.sample_text),
                            fontSize = 36.sp,
                            lineHeight = 36.sp,
                            fontFamily = fontFamily
                        )
                    }
                    IconButton(
                        onClick = {
                            onFavoriteClick(it)
                        },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            painter = rememberAnimatedVectorPainter(
                                avdHeartFill,
                                it.isFavorite
                            ),
                            contentDescription = null,
                            tint = if (it.isFavorite) {
                                MaterialTheme.colorScheme.error
                            } else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
