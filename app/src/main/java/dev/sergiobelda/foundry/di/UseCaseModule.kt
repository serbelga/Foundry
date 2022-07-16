package dev.sergiobelda.foundry.di

import dev.sergiobelda.foundry.domain.usecase.FetchFontsUseCase
import dev.sergiobelda.foundry.domain.usecase.GetFontItemsUseCase
import dev.sergiobelda.foundry.domain.usecase.InsertFavoriteFontUseCase
import dev.sergiobelda.foundry.domain.usecase.RemoveFavoriteFontUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single {
        FetchFontsUseCase(get())
    }
    single {
        GetFontItemsUseCase(get())
    }
    single {
        InsertFavoriteFontUseCase(get())
    }
    single {
        RemoveFavoriteFontUseCase(get())
    }
}
