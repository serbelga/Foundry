package dev.sergiobelda.foundry.di

import dev.sergiobelda.foundry.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(get(), get(), get(), get())
    }
}
