package dev.sergiobelda.foundry.di

import androidx.room.Room
import dev.sergiobelda.foundry.data.database.FoundryDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), FoundryDatabase::class.java, "FontsDatabase.db")
            .build()
    }
    single {
        get<FoundryDatabase>().favoriteFontsDao()
    }
    single {
        get<FoundryDatabase>().googleFontsDao()
    }
}
