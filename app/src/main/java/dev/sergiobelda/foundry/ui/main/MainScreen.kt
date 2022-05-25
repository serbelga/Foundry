package dev.sergiobelda.foundry.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.TextFields
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = getViewModel()
) {
    var selected by remember { mutableStateOf(0) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    @OptIn(ExperimentalTextApi::class)
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    val appFontFamily = FontFamily(
        Font(googleFont = GoogleFont("Questrial"), fontProvider = provider)
    )

    Scaffold(
        topBar = {
            Column {
                SmallTopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            fontFamily = appFontFamily
                        )
                    }
                )
                if (mainViewModel.mainUiState.isLoadingFonts) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selected == 0,
                    onClick = { selected = 0 },
                    icon = { Icon(Icons.Rounded.TextFields, contentDescription = null) },
                    label = { Text(text = "Fonts", fontFamily = appFontFamily) }
                )
                NavigationBarItem(
                    selected = selected == 1,
                    onClick = { selected = 1 },
                    icon = { Icon(Icons.Rounded.Favorite, contentDescription = null) },
                    label = { Text(text = "Favorites", fontFamily = appFontFamily) }
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
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(paddingValue)
        ) {
            items(mainViewModel.mainUiState.fonts) {
                val fontName = GoogleFont(it.name)
                val fontFamily = FontFamily(
                    Font(googleFont = fontName, fontProvider = provider)
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { }
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = it.name,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(bottom = 12.dp),
                                fontFamily = appFontFamily
                            )
                            Text(
                                text = "Almost before we knew it, we had left the ground.",
                                fontSize = 36.sp,
                                lineHeight = 36.sp,
                                fontFamily = fontFamily
                            )
                        }
                        IconButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            /*
                            if (it.favorite) {
                                Icon(Icons.Rounded.Favorite, contentDescription = null)
                            } else {
                                Icon(Icons.Rounded.FavoriteBorder, contentDescription = null)
                            }
                            */
                        }
                    }
                }
            }
        }
    }
}
