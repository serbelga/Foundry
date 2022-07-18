package dev.sergiobelda.foundry.ui.main

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.TextFields
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.pinnedScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.sergiobelda.foundry.R
import dev.sergiobelda.foundry.domain.model.FontItemModel
import dev.sergiobelda.foundry.ui.theme.fontFamily
import dev.sergiobelda.foundry.ui.theme.pacificoFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun MainScreen(mainViewModel: MainViewModel = getViewModel()) {
    var navigationView by remember { mutableStateOf(NavigationView.FONT_LIST_VIEW) }
    val scrollBehavior = remember { pinnedScrollBehavior() }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    @OptIn(ExperimentalTextApi::class)
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            fontFamily = pacificoFontFamily
                        )
                    },
                    scrollBehavior = scrollBehavior
                )
                if (mainViewModel.mainUiState.isFetchingFonts) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = navigationView == NavigationView.FONT_LIST_VIEW,
                    onClick = { navigationView = NavigationView.FONT_LIST_VIEW },
                    icon = { Icon(Icons.Rounded.TextFields, contentDescription = null) },
                    label = { Text(text = "Fonts", fontFamily = fontFamily) }
                )
                NavigationBarItem(
                    selected = navigationView == NavigationView.FAVORITES_SCREEN,
                    onClick = { navigationView = NavigationView.FAVORITES_SCREEN },
                    icon = { Icon(Icons.Rounded.Favorite, contentDescription = null) },
                    label = { Text(text = "Favorites", fontFamily = fontFamily) }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch { listState.animateScrollToItem(0) }
                }
            ) {
                Icon(Icons.Rounded.ArrowUpward, contentDescription = null)
            }
        }
    ) { paddingValue ->
        when (navigationView) {
            NavigationView.FONT_LIST_VIEW -> {
                FontListView(
                    listState,
                    mainViewModel.mainUiState.fontItems,
                    provider,
                    onFavoriteClick = { mainViewModel.updateFontFavoriteState(it) },
                    modifier = Modifier.padding(paddingValue)
                )
            }
            NavigationView.FAVORITES_SCREEN -> {
                FontListView(
                    listState,
                    mainViewModel.mainUiState.favoriteFontItems,
                    provider,
                    onFavoriteClick = { mainViewModel.updateFontFavoriteState(it) },
                    modifier = Modifier.padding(paddingValue)
                )
            }
        }
    }
}

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
            val avdHeartBreak =
                AnimatedImageVector.animatedVectorResource(R.drawable.avd_heart_break)

            var atEnd by remember {
                mutableStateOf(false)
            }

            val scope = rememberCoroutineScope()

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
                            scope.launch {
                                atEnd = true
                                delay(900)
                                onFavoriteClick(it)
                                atEnd = false
                            }
                        },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        if (it.isFavorite) {
                            Icon(
                                painter = rememberAnimatedVectorPainter(
                                    avdHeartBreak,
                                    atEnd
                                ),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                        } else {
                            Icon(
                                painter = rememberAnimatedVectorPainter(
                                    avdHeartFill,
                                    atEnd
                                ),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}
