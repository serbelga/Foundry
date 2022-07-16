package dev.sergiobelda.foundry.di

import dev.sergiobelda.foundry.data.localdatasource.FontLocalDataSource
import dev.sergiobelda.foundry.data.localdatasource.IFontLocalDataSource
import org.koin.dsl.module

val localDataSourceModule = module {
    single<IFontLocalDataSource> {
        FontLocalDataSource(get(), get())
    }
}
