package dev.sergiobelda.foundry.di

import dev.sergiobelda.foundry.data.repository.FontRepository
import dev.sergiobelda.foundry.domain.repository.IFontRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<IFontRepository> { FontRepository(get(), get()) }
}
