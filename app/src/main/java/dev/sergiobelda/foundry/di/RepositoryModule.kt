package dev.sergiobelda.foundry.di

import dev.sergiobelda.foundry.data.repository.FontsRepository
import dev.sergiobelda.foundry.domain.repository.IFontsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<IFontsRepository> { FontsRepository(get(), get()) }
}
