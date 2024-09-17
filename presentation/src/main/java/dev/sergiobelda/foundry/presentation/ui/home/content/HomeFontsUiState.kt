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

package dev.sergiobelda.foundry.presentation.ui.home.content

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import dev.sergiobelda.foundry.presentation.ui.resources.FAB_VISIBLE_ITEM_INDEX
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberHomeFontsUiState(
    lazyListState: LazyListState = rememberLazyListState(),
    sheetState: SheetState = rememberModalBottomSheetState()
): HomeFontsUiState {
    val coroutineScope = rememberCoroutineScope()
    return remember(
        lazyListState,
        sheetState,
        coroutineScope
    ) {
        HomeFontsUiState(
            lazyListState = lazyListState,
            sheetState = sheetState,
            coroutineScope = coroutineScope
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Stable
class HomeFontsUiState internal constructor(
    val lazyListState: LazyListState,
    val sheetState: SheetState,
    private val coroutineScope: CoroutineScope
) {

    // TODO: Add to Saver
    var openBottomSheet by mutableStateOf(false)
        private set

    val fabVisible by derivedStateOf {
        lazyListState.firstVisibleItemIndex >= FAB_VISIBLE_ITEM_INDEX
    }

    fun openBottomSheet() {
        openBottomSheet = true
    }

    fun closeBottomSheet() {
        openBottomSheet = false
    }

    fun animateScrollToTop() {
        coroutineScope.launch {
            lazyListState.animateScrollToItem(0)
        }
    }
}
