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

package dev.sergiobelda.foundry.ui.home.search

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import dev.sergiobelda.foundry.R

@OptIn(ExperimentalAnimationGraphicsApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun HomeSearchBar(
    onMenuIconButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    val avdMenuToArrowBack = AnimatedImageVector.animatedVectorResource(R.drawable.avd_menu_to_arrow_back)
    val avdMenuToArrowBackPainter = rememberAnimatedVectorPainter(avdMenuToArrowBack, active)
    val focusManager = LocalFocusManager.current
    val onActiveChange: (Boolean) -> Unit = {
        active = it
        if (!active) focusManager.clearFocus()
    }
    fun closeSearchBar() {
        focusManager.clearFocus()
        active = false
        searchText = ""
    }
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = searchText,
                onQueryChange = { searchText = it },
                onSearch = { closeSearchBar() },
                expanded = active,
                onExpandedChange = onActiveChange,
                placeholder = { Text(stringResource(id = R.string.search_fonts)) },
                leadingIcon = {
                    if (active) {
                        IconButton(onClick = { closeSearchBar() }) {
                            Icon(
                                painter = avdMenuToArrowBackPainter,
                                contentDescription = null,
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {
                                if (active) {
                                    closeSearchBar()
                                } else {
                                    onMenuIconButtonClick()
                                }
                            },
                        ) {
                            Icon(
                                painter = avdMenuToArrowBackPainter,
                                contentDescription = null,
                            )
                        }
                    }
                },
                trailingIcon = {
                    if (active) {
                        IconButton(onClick = { searchText = "" }) {
                            Icon(Icons.Rounded.Clear, contentDescription = null)
                        }
                    }
                },
            )
        },
        expanded = active,
        onExpandedChange = onActiveChange,
        modifier = modifier.fillMaxWidth(),
        content = {},
    )
}
