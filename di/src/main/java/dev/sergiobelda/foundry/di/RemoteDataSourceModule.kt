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

import dev.sergiobelda.foundry.data.remotedatasource.FontRemoteDataSource
import dev.sergiobelda.foundry.data.remotedatasource.FontsRemoteDataSourceMapper
import dev.sergiobelda.foundry.data.remotedatasource.google.GoogleFontsRemoteDataSource
import dev.sergiobelda.foundry.data.remotedatasource.google.GoogleFontsRemoteDataSourceMapper
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val remoteDataSourceModule =
    module {
        singleOf(::GoogleFontsRemoteDataSourceMapper) bind FontsRemoteDataSourceMapper::class
        singleOf(::GoogleFontsRemoteDataSource) bind FontRemoteDataSource::class
    }
