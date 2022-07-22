package dev.sergiobelda.foundry.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.googlefonts.GoogleFont
import dev.sergiobelda.foundry.R
import dev.sergiobelda.foundry.ui.components.FontListView
import dev.sergiobelda.foundry.ui.theme.pacificoFontFamily
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun FavoritesScreen(favoritesViewModel: FavoritesViewModel = getViewModel()) {
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

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
        FontListView(
            listState,
            favoritesViewModel.favoritesUiState.favoriteFontItems,
            provider,
            onFavoriteClick = { favoritesViewModel.updateFontFavoriteState(it) },
            modifier = Modifier.padding(paddingValue)
        )
    }
}
