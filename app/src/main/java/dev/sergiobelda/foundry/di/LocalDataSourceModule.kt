package dev.sergiobelda.foundry.di

import dev.sergiobelda.foundry.data.remotedatasource.FontRemoteDataSource
import dev.sergiobelda.foundry.data.remotedatasource.IFontRemoteDataSource
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single<IFontRemoteDataSource> {
        FontRemoteDataSource(get())
    }
}
