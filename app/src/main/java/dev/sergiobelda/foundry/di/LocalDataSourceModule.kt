package dev.sergiobelda.foundry.di

import dev.sergiobelda.foundry.data.remotedatasource.FontsRemoteDataSource
import dev.sergiobelda.foundry.data.remotedatasource.IFontsRemoteDataSource
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single<IFontsRemoteDataSource> {
        FontsRemoteDataSource(get())
    }
}
