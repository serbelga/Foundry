package dev.sergiobelda.foundry.di

import dev.sergiobelda.foundry.data.localdatasource.FontsLocalDataSource
import dev.sergiobelda.foundry.data.localdatasource.IFontsLocalDataSource
import org.koin.dsl.module

val localDataSourceModule = module {
    single<IFontsLocalDataSource> {
        FontsLocalDataSource(get())
    }
}
