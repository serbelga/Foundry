package dev.sergiobelda.foundry.di

import dev.sergiobelda.foundry.domain.usecase.FetchFontsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single {
        FetchFontsUseCase(get())
    }
}
