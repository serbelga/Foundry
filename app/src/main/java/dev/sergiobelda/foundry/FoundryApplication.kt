/*
 * Copyright 2022 Sergio Belda Galbis
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

package dev.sergiobelda.foundry

import android.app.Application
import dev.sergiobelda.foundry.di.databaseModule
import dev.sergiobelda.foundry.di.localDataSourceModule
import dev.sergiobelda.foundry.di.networkModule
import dev.sergiobelda.foundry.di.remoteDataSourceModule
import dev.sergiobelda.foundry.di.repositoryModule
import dev.sergiobelda.foundry.di.useCaseModule
import dev.sergiobelda.foundry.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FoundryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FoundryApplication)
            modules(
                databaseModule,
                localDataSourceModule,
                networkModule,
                remoteDataSourceModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}
