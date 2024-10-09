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

package dev.sergiobelda.foundry.di

import dev.sergiobelda.foundry.domain.usecase.FetchFontsUseCase
import dev.sergiobelda.foundry.domain.usecase.GetFontFamilyItemsUseCase
import dev.sergiobelda.foundry.domain.usecase.GetSavedFontFamilyItemsUseCase
import dev.sergiobelda.foundry.domain.usecase.RemoveLikedFontFamilyUseCase
import dev.sergiobelda.foundry.domain.usecase.LikeFontFamilyUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule =
    module {
        singleOf(::FetchFontsUseCase)
        singleOf(::GetFontFamilyItemsUseCase)
        singleOf(::GetSavedFontFamilyItemsUseCase)
        singleOf(::LikeFontFamilyUseCase)
        singleOf(::RemoveLikedFontFamilyUseCase)
    }