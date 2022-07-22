package dev.sergiobelda.foundry.di

import dev.sergiobelda.foundry.ui.favorites.FavoritesViewModel
import dev.sergiobelda.foundry.ui.fonts.FontsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        FontsViewModel(get(), get(), get(), get())
    }
    viewModel {
        FavoritesViewModel(get(), get(), get())
    }
}
