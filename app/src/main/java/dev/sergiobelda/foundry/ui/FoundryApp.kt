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

package dev.sergiobelda.foundry.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dev.sergiobelda.foundry.navigation.Action
import dev.sergiobelda.foundry.navigation.FoundryNavHost
import dev.sergiobelda.foundry.ui.theme.FoundryTheme

@Composable
fun FoundryApp() {
    val navController = rememberNavController()
    FoundryNavHost(navController = navController)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FoundryTheme {
        FoundryApp()
    }
}
