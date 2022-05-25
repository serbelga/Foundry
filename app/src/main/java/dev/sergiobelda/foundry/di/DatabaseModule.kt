package dev.sergiobelda.foundry.di

import androidx.room.Room
import dev.sergiobelda.foundry.data.database.FontsDatabase
import dev.sergiobelda.foundry.data.database.dao.FontsDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), FontsDatabase::class.java, "FontsDatabase.db")
            .build()
    }
    single<FontsDao> {
        get<FontsDatabase>().fontsDao()
    }
}
